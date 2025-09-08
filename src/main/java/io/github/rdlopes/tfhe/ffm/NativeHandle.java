package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.ffm.NativeCall.executeWithAddress;

public record NativeHandle(
  Class<?> javaClass,
  MemorySegment address,
  Function<MemorySegment, Integer> destroyer) implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(NativeHandle.class);

  public NativeHandle {
    logger.trace("init - javaClass: {}, address: 0x{}, destroyer: {}", javaClass.getSimpleName(), Long.toHexString(address.address()), destroyer);
  }

  @Override
  public void run() {
    logger.debug("destroy {}[0x{}]", javaClass.getSimpleName(), Long.toHexString(address.address()));
    executeWithAddress(address, destroyer);
  }
}
