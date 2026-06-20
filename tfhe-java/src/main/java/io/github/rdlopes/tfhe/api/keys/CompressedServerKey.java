package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.CompressedFheKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.api.serde.DynamicBuffer.MAX_SERIALIZATION_SIZE;
import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

/// The canonical server key, shareable with remote servers and usable with both CPU and GPU.
///
/// `CompressedServerKey` is the pivot of the key architecture:
/// from a single instance you can produce a CPU [ServerKey] (via [#decompress()]) or a
/// GPU `CudaServerKey` (via [#decompressToGpu()]). It is the **only** key type that
/// supports transparent GPU acceleration.
///
/// ## Activating the key — [#use()]
///
/// [#use()] is the single entry point for registering the key with the TFHE runtime.
/// It always activates the CPU server key, and additionally activates the CUDA server key
/// when the system property `tfhe.gpu=true` is set — all with lazy caching.
///
/// **Client side** (from a [KeySet]):
///
/// {@snippet lang=java :
/// keySet.getCompressedServerKey().use();
/// }
///
/// **Server side** (after receiving serialized bytes from the client):
///
/// {@snippet lang=java :
/// CompressedServerKey serverKey = CompressedServerKey.deserialize(receivedBytes);
/// serverKey.use(); // CPU, or CPU+GPU when -Dtfhe.gpu=true
/// }
///
/// ## Serialization
///
/// Serialize this key to send it to a remote server. The server can then call [#use()]
/// regardless of whether it uses CPU or GPU — no additional configuration needed beyond
/// the `tfhe.gpu` system property.
///
/// {@snippet lang=java :
/// // Client
/// DynamicBuffer bytes = keySet.getCompressedServerKey().serialize();
/// sendToServer(bytes);
///
/// // Server
/// CompressedServerKey serverKey = CompressedServerKey.deserialize(bytes);
/// serverKey.use();
/// }
///
/// ## GPU note
///
/// This is the **only** key format that supports GPU activation.
/// A [ServerKey] obtained from [ServerKey#deserialize(DynamicBuffer)] is always CPU-only.
///
/// @see KeySet the client-side key container
/// @see io.github.rdlopes.tfhe.api.keys the package-level guide with full usage scenarios
public class CompressedServerKey extends NativePointer implements CompressedFheKey<ServerKey> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedServerKey.class);

  /// Lazily-decompressed CPU key. Created on the first call to [#use()] or [#getCpuKey()].
  private @Nullable ServerKey cpuKey;

  /// Lazily-decompressed GPU key. Created on the first call to [#use()] when `tfhe.gpu=true`.
  private @Nullable CudaServerKey cudaKey;

  CompressedServerKey() {
    logger.trace("init");
    super(TfheHeader::compressed_server_key_destroy);
  }

  public CompressedServerKey(ClientKey clientKey) {
    this();
    logger.trace("init - from ClientKey");
    execute(() -> compressed_server_key_new(clientKey.getValue(), getAddress()));
  }

  /// Deserializes a [CompressedServerKey] from the given buffer.
  ///
  /// After deserialization, call [#use()] to activate the key for FHE operations.
  public static CompressedServerKey deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize - dynamicBuffer: {}", dynamicBuffer);
    CompressedServerKey deserialized = new CompressedServerKey();
    execute(() -> compressed_server_key_safe_deserialize(dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_server_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE));
    return dynamicBuffer;
  }

  /// Decompresses this key into a new CPU [ServerKey].
  /// Each call returns a fresh instance; the caller owns its lifecycle.
  ///
  /// For activating FHE operations, prefer [#use()] which also handles GPU.
  @Override
  public ServerKey decompress() {
    logger.trace("decompress");
    return new ServerKey(this);
  }

  /// Decompresses this key into a new GPU `CudaServerKey`.
  /// Each call returns a fresh instance; the caller owns its lifecycle.
  public CudaServerKey decompressToGpu() {
    logger.trace("decompressToGpu");
    CudaServerKey cuda = new CudaServerKey();
    execute(() -> compressed_server_key_decompress_to_gpu(getValue(), cuda.getAddress()));
    return cuda;
  }

  /// Registers this key with the TFHE runtime, transparently handling CPU and GPU.
  ///
  /// Always sets the CPU server key on the calling thread.
  /// When `tfhe.gpu=true`, also initialises a dedicated GPU executor thread
  /// (via [GpuRouter#initGpu(ServerKey, CudaServerKey)]) that has both CPU and CUDA
  /// keys set — enabling GPU-accelerated operations without affecting CPU-only calls
  /// on the calling thread.
  ///
  /// Both derived keys are cached lazily — decompression occurs only once.
  public void use() {
    logger.trace("use");
    if (Boolean.getBoolean("tfhe.gpu")) {
      // Delegate GPU key management to GpuRouter:
      // - calling thread gets CPU key only
      // - dedicated GPU executor thread gets CPU + CUDA keys
      io.github.rdlopes.tfhe.ffm.GpuRouter.initGpu(getCpuKey().getValue(), getCudaKey().getValue());
    } else {
      execute(() -> set_server_key(getCpuKey().getValue()));
    }
  }

  /// Returns the lazily-cached CPU [ServerKey].
  /// Package-private so [KeySet] can expose it via [KeySet#getServerKey()]
  /// without creating a duplicate object.
  ServerKey getCpuKey() {
    if (cpuKey == null) {
      cpuKey = new ServerKey(this);
    }
    return cpuKey;
  }

  private CudaServerKey getCudaKey() {
    if (cudaKey == null) {
      cudaKey = decompressToGpu();
    }
    return cudaKey;
  }

  /// Destroys the cached CPU and GPU derived keys, then the compressed key itself.
  @Override
  public void destroy() {
    if (cpuKey != null) {
      cpuKey.destroy();
      cpuKey = null;
    }
    if (cudaKey != null) {
      cudaKey.destroy();
      cudaKey = null;
    }
    super.destroy();
  }
}
