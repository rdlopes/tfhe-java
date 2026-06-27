package io.github.rdlopes.tfhe.core.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;

/// Cleaner action that frees a native resource when the owning [NativeAddress]
/// is garbage-collected or explicitly destroyed.
///
/// The [NativeLifecycle] token is shared with the owning [NativeAddress].
/// If [NativeAddress#release()] has been called (ownership transferred to native),
/// or if [NativeAddress#destroy()] ran first, the [#run()] method is a no-op.
public record NativeHandle(
  Class<?> javaClass,
  MemorySegment address,
  Function<MemorySegment, Integer> destroyer,
  NativeLifecycle lifecycle) implements Runnable {
  
  private static final Logger logger = LoggerFactory.getLogger(NativeHandle.class);

  public NativeHandle {
    if (logger.isTraceEnabled()) {
      logger.trace(
        "init - javaClass: {}, address: {}, destroyer: {}, lifecycle: {}",
        javaClass, address, destroyer, lifecycle
      );
    }
  }

  @Override
  public void run() {
    if (lifecycle.isReleased()) {
      if (logger.isTraceEnabled()) {
        logger.trace(
          "run {}[0x{}] - skipped (released)",
          javaClass.getSimpleName(), Long.toHexString(address.address())
        );
      }
      return;
    }
    if (logger.isTraceEnabled()) {
      logger.trace("run {}[0x{}]", javaClass.getSimpleName(), Long.toHexString(address.address()));
    }
    lifecycle.release(); // mark before calling to prevent re-entrancy
    execute(() -> destroyer.apply(address));
  }
}
