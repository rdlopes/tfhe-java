package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.C_POINTER;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;

public final class ConfigBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(ConfigBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(C_POINTER);
  }
}
