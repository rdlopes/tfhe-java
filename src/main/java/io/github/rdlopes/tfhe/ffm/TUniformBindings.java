package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.new_t_uniform;

public final class TUniformBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(TUniformBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(TUniform.layout());
  }

  public static MemorySegment allocate(int boundLog2) {
    logger.trace("allocate - boundLog2: {}", boundLog2);
    return new_t_uniform(LIBRARY_ARENA, boundLog2);
  }

  public static void boundLog2(MemorySegment address, int boundLog2) {
    logger.trace("boundLog2 - address: {}, boundLog2: {}", address, boundLog2);
    TUniform.bound_log2(address, boundLog2);
  }

  public static int boundLog2(MemorySegment address) {
    logger.trace("boundLog2 - address: {}", address);
    return TUniform.bound_log2(address);
  }
}
