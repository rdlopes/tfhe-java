package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;

public final class DynamicBufferViewBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(DynamicBufferViewBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(DynamicBufferView.layout());
  }

  public static MemorySegment pointer(MemorySegment address) {
    logger.trace("pointer - address: {}", address);
    return DynamicBufferView.pointer(address);
  }

  public static void pointer(MemorySegment address, MemorySegment value) {
    logger.trace("pointer - address: {}, value: {}", address, value);
    DynamicBufferView.pointer(address, value);
  }

  public static long length(MemorySegment address) {
    logger.trace("length - address: {}", address);
    return DynamicBufferView.length(address);
  }

  public static void length(MemorySegment address, long value) {
    logger.trace("length - address: {}, value: {}", address, value);
    DynamicBufferView.length(address, value);
  }
}
