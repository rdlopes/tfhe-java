package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;

import java.util.concurrent.atomic.AtomicBoolean;

public record NativeHandle(
  Class<?> javaClass,
  MemorySegment address,
  Function<MemorySegment, Integer> destroyer,
  AtomicBoolean released) implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(NativeHandle.class);

  public NativeHandle {
    logger.trace("init - javaClass: {}, address: {}, destroyer: {}, released: {}", javaClass, address, destroyer, released);
  }

  @Override
  public void run() {
    if (released.get()) {
      logger.trace("run {}[0x{}] - skipped (released)", javaClass.getSimpleName(), Long.toHexString(address.address()));
      return;
    }
    logger.trace("run {}[0x{}]", javaClass.getSimpleName(), Long.toHexString(address.address()));

    execute(() -> destroyer.apply(address));
  }

}
