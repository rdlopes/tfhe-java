package io.github.rdlopes.tfhe.ffm;

import io.github.rdlopes.tfhe.utils.NativeLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

public class TfheNative extends TfheHeader {
  private static final Logger logger = LoggerFactory.getLogger(TfheNative.class);

  static {
    NativeLibrary.load();
  }

  public static MemorySegment createPointer(MemoryLayout layout) {
    logger.trace("createPointer - layout: {}", layout.name());

    return LIBRARY_ARENA.allocate(layout);
  }

  public static MemorySegment createPointer(long byteSize) {
    logger.trace("createPointer - byteSize: {}", byteSize);

    return LIBRARY_ARENA.allocate(byteSize);
  }

  public static MemorySegment createPointer(MemoryLayout layout, long count) {
    logger.trace("createPointer - layout: {}, count: {}", layout.name(), count);

    return LIBRARY_ARENA.allocate(layout, count);
  }

}
