package io.github.rdlopes.tfhe.core.ffm;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.ref.Cleaner;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.LIBRARY_ARENA;
import static java.lang.ref.Cleaner.Cleanable;
import static java.lang.ref.Cleaner.create;

/// Base class for all heap-allocated native resources.
///
/// ## Lifecycle
///
/// Every [NativeAddress] instance registers a [NativeHandle] with a
/// [Cleaner]. If the caller calls [#close()] (preferred) or [#destroy()],
/// the native destructor runs immediately and the Cleaner registration is cancelled.
/// If the caller forgets, the GC will eventually trigger the Cleaner as a safety net.
///
/// ## AutoCloseable
///
/// All subclasses are usable in try-with-resources blocks via the inherited
/// [#close()] method, which delegates to [#destroy()].
public class NativeAddress implements AutoCloseable {
  
  
  private static final Logger logger = LoggerFactory.getLogger(NativeAddress.class);
  private static final Cleaner CLEANER = create();

  static {
    NativeLibrary.load();
    System.setProperty("jextract.trace.downcalls", String.valueOf(logger.isTraceEnabled()));
  }

  private final MemorySegment address;
  private final Cleanable cleanable;
  /// Shared lifecycle state — also held by [NativeHandle] for the Cleaner callback.
  private final NativeLifecycle lifecycle = new NativeLifecycle();

  protected NativeAddress(MemorySegment address, @Nullable Function<MemorySegment, Integer> destroyer) {
    this.address = address;
    this.cleanable = destroyer != null
      ? CLEANER.register(this, new NativeHandle(getClass(), address, destroyer, lifecycle))
      : null;
  }
  
  protected NativeAddress(Function<SegmentAllocator, MemorySegment> allocator,
                          @Nullable Function<MemorySegment, Integer> destroyer) {
    this(allocator.apply(LIBRARY_ARENA), destroyer);
  }

  public MemorySegment getAddress() {
    return address;
  }
  
  /// Explicitly releases the native resource immediately.
  /// Calling this method more than once is safe (idempotent).
  /// Prefer try-with-resources over calling this method directly.
  public void destroy() {
    if (cleanable != null) {
      cleanable.clean();
    }
  }
  
  /// Implements [AutoCloseable]; delegates to [#destroy()].
  /// Enables try-with-resources for all FHE objects, keys, and buffers.
  @Override
  public void close() {
    destroy();
  }
  
  /// Marks this resource as released without calling the native destructor.
  /// Used when ownership is transferred to a native function.
  public void release() {
    lifecycle.release();
  }
}
