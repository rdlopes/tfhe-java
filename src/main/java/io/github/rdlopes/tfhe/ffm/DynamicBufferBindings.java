package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;

public final class DynamicBufferBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(DynamicBufferBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(DynamicBuffer.layout());
  }

  public static MemorySegment pointer(MemorySegment address) {
    logger.trace("pointer - address: {}", address);
    return DynamicBuffer.pointer(address);
  }

  public static void pointer(MemorySegment address, MemorySegment value) {
    logger.trace("pointer - address: {}, value: {}", address, value);
    DynamicBuffer.pointer(address, value);
  }

  public static long length(MemorySegment address) {
    logger.trace("length - address: {}", address);
    return DynamicBuffer.length(address);
  }

  public static void length(MemorySegment address, long value) {
    logger.trace("length - address: {}, value: {}", address, value);
    DynamicBuffer.length(address, value);
  }

  public static MemorySegment destructor(MemorySegment address) {
    logger.trace("destructor - address: {}", address);
    return DynamicBuffer.destructor(address);
  }

  public static void destructor(MemorySegment address, MemorySegment value) {
    logger.trace("destructor - address: {}, value: {}", address, value);
    DynamicBuffer.destructor(address, value);
  }
}
