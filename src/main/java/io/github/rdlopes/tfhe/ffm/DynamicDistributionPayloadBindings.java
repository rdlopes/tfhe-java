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

  public static void gaussian(MemorySegment address, MemorySegment gaussian) {
    logger.trace("gaussian - address: {}, gaussian: {}", address, gaussian);
    DynamicDistributionPayload.gaussian(address, gaussian);
  }

  public static void tUniform(MemorySegment address, MemorySegment tUniform) {
    logger.trace("tUniform - address: {}, tUniform: {}", address, tUniform);
    DynamicDistributionPayload.t_uniform(address, tUniform);
  }

  public static MemorySegment gaussian(MemorySegment address) {
    logger.trace("gaussian - address: {}", address);
    return DynamicDistributionPayload.gaussian(address);
  }

  public static MemorySegment tUniform(MemorySegment address) {
    logger.trace("tUniform - address: {}", address);
    return DynamicDistributionPayload.t_uniform(address);
  }
}
