package io.github.rdlopes.tfhe.ffm;

import io.github.rdlopes.tfhe.api.TfheThreadingContext;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.set_cuda_server_key;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.set_server_key;


/// Routes FHE operations to the correct server-key context **before** the native call is made.
///
/// ## Design rationale
///
/// The TFHE-rs CUDA backend does not fall back to CPU for unsupported operations.
/// When `set_cuda_server_key` is active on a thread, operations that CUDA does not
/// implement (e.g. `compress()`, signed-integer arithmetic, `overflowing_mul`) will
/// fail with a native panic rather than transparently using the CPU key.
///
/// `GpuRouter` resolves this by maintaining **two independent key contexts**:
///
/// - The **calling thread** has only the CPU server key set (`set_server_key`).
///   CPU-only operations (compress, signed integers, `overflowing_mul`) run here
///   naturally — no CUDA key is present to interfere.
/// - A **dedicated GPU executor thread** has both the CPU and CUDA server keys set.
///   GPU-capable operations are submitted to this thread and run with CUDA acceleration.
///
/// This is **a-priori routing** — the correct key context is chosen at the call site,
/// never recovered from an error.
///
/// ## Capability enum
///
/// The [Capability] enum expresses whether an operation is GPU-capable:
/// - [Capability#GPU] — sent to the GPU executor thread (CUDA-accelerated when enabled)
/// - [Capability#CPU] — executed on the calling thread (CPU ServerKey only)
///
/// ## Activation
///
/// [#initGpu(ServerKey, CudaServerKey)] is called once by [io.github.rdlopes.tfhe.api.keys.CompressedServerKey#use()]
/// when `tfhe.gpu=true`. Subsequent calls are no-ops (the executor is initialised only once).
///
/// @see io.github.rdlopes.tfhe.api.AbstractFheType
/// @see io.github.rdlopes.tfhe.api.keys.CompressedServerKey
public final class GpuRouter {

    private static final Logger logger = LoggerFactory.getLogger(GpuRouter.class);

    /// GPU capability of an FHE operation.
    ///
    /// - [#GPU] — the TFHE-rs CUDA backend supports this operation.
    /// - [#CPU] — the operation is CPU-only (must not run on a thread where
    ///   `set_cuda_server_key` has been called).
    public enum Capability {
        /// Operation is supported by the TFHE-rs CUDA backend.
        GPU,
        /// Operation is CPU-only.
        ///
        /// Known CPU-only operations in the current TFHE-rs CUDA release:
        /// - `compress()` on individual ciphertexts (use `CompressedCiphertextList` on GPU)
        /// - All signed-integer types (`FheInt*`)
        /// - `overflowing_mul` (`multiplyWithOverflow`)
        CPU
    }

    /// The dedicated GPU executor thread. `null` when GPU mode is not active.
    ///
    /// This thread has both `set_server_key` and `set_cuda_server_key` called at
    /// initialisation and is the only thread where CUDA-accelerated operations run.
    private static volatile @Nullable ExecutorService gpuExecutor = null;

    private GpuRouter() {}

    // ──────────────────────────────────────────────────────────────────────────
    // Initialisation
    // ──────────────────────────────────────────────────────────────────────────

    /// Initialises the GPU routing context (called once by [io.github.rdlopes.tfhe.api.keys.CompressedServerKey#use()]).
    ///
    /// Sets the CPU server key on the **calling thread**.
    /// If `gpuExecutor` has not yet been initialised, creates a single-thread executor
    /// and sets both CPU and CUDA keys on that thread.
    ///
    /// Subsequent calls with different keys update the calling thread's CPU key but do
    /// not re-initialise the executor.
    ///
    /// @param cpuKeyValue  the native [MemorySegment] of the CPU [io.github.rdlopes.tfhe.api.keys.ServerKey]
    /// @param cudaKeyValue the native [MemorySegment] of the [io.github.rdlopes.tfhe.api.keys.CudaServerKey]
    public static void initGpu(MemorySegment cpuKeyValue, MemorySegment cudaKeyValue) {
        logger.trace("initGpu");

        // Always set CPU key on the calling thread (no CUDA key here)
        NativeCall.execute(() -> set_server_key(cpuKeyValue));

        // Initialise the dedicated GPU executor thread (lazy, once per JVM)
        if (gpuExecutor == null) {
            synchronized (GpuRouter.class) {
                if (gpuExecutor == null) {
                    logger.info("initGpu - creating GPU executor thread");
                    gpuExecutor = Executors.newSingleThreadExecutor(r -> {
                        Thread t = new Thread(r, "tfhe-gpu-executor");
                        t.setDaemon(true);
                        return t;
                    });
                }
            }
        }

        // Always update CPU and CUDA keys on the GPU executor thread
        try {
            gpuExecutor.submit(() -> {
                NativeCall.execute(() -> set_server_key(cpuKeyValue));
                NativeCall.execute(() -> set_cuda_server_key(cudaKeyValue));
                logger.debug("initGpu - GPU executor thread keys set/updated");
            }).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("GPU executor key update was interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("GPU executor key update failed", e.getCause());
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Routing — execute (void result)
    // ──────────────────────────────────────────────────────────────────────────

    /// Executes a void native FHE operation with the appropriate key context.
    ///
    /// - [Capability#GPU] with GPU active → submitted to the GPU executor thread (CUDA)
    /// - [Capability#CPU] or GPU not active → executed on the calling thread (CPU key)
    ///
    /// @param capability the GPU capability of the operation
    /// @param supplier   the native call, returning `0` on success
    public static void execute(Capability capability, Supplier<Integer> supplier) {
        ExecutorService ex = gpuExecutor;
        if (capability == Capability.GPU && ex != null && !TfheThreadingContext.IN_THREADING_CONTEXT.get()) {
            logger.trace("execute - routing to GPU executor");
            submitToGpu(() -> {
                NativeCall.execute(supplier);
                return null;
            });
        } else {
            logger.trace("execute - running on calling thread (CPU)");
            NativeCall.execute(supplier);
        }
    }

    // ──────────────────────────────────────────────────────────────────────────
    // Routing — executeAndReturn (value result)
    // ──────────────────────────────────────────────────────────────────────────

    /// Executes a native FHE operation that returns a value, with the appropriate key context.
    ///
    /// @param capability  the GPU capability of the operation
    /// @param returnType  the Java type of the return value
    /// @param setter      the native call that writes its result into the provided [MemorySegment]
    /// @param <R>         the return type
    /// @return the operation result
    public static <R> R executeAndReturn(Capability capability, Class<R> returnType, Function<MemorySegment, Integer> setter) {
        ExecutorService ex = gpuExecutor;
        if (capability == Capability.GPU && ex != null && !TfheThreadingContext.IN_THREADING_CONTEXT.get()) {
            logger.trace("executeAndReturn - routing to GPU executor");
            return submitToGpu(() -> NativeCall.executeAndReturn(returnType, setter));
        }
        logger.trace("executeAndReturn - running on calling thread (CPU)");
        return NativeCall.executeAndReturn(returnType, setter);
    }

    private static <T> T submitToGpu(java.util.concurrent.Callable<T> callable) {
        try {
            return gpuExecutor.submit(callable).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("GPU operation interrupted", e);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NativeCallException nce) throw nce;
            if (cause instanceof RuntimeException re) throw re;
            throw new RuntimeException("GPU operation failed", cause != null ? cause : e);
        }
    }
}
