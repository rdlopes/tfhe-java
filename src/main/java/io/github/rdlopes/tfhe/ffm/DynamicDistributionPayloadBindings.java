package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;

public final class DynamicDistributionPayloadBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(DynamicDistributionPayloadBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(DynamicDistributionPayload.layout());
  }

  public static void gaussian(MemorySegment pointer, MemorySegment gaussian) {
    logger.trace("gaussian - pointer: {}, gaussian: {}", pointer, gaussian);
    DynamicDistributionPayload.gaussian(pointer, gaussian);
  }

  public static void tUniform(MemorySegment pointer, MemorySegment tUniform) {
    logger.trace("tUniform - pointer: {}, tUniform: {}", pointer, tUniform);
    DynamicDistributionPayload.t_uniform(pointer, tUniform);
  }

  public static MemorySegment gaussian(MemorySegment pointer) {
    logger.trace("gaussian - pointer: {}", pointer);
    return DynamicDistributionPayload.gaussian(pointer);
  }

  public static MemorySegment tUniform(MemorySegment pointer) {
    logger.trace("tUniform - pointer: {}", pointer);
    return DynamicDistributionPayload.t_uniform(pointer);
  }
}
