package io.github.rdlopes.tfhe.ffm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.LIBRARY_ARENA;

public final class DynamicDistributionBindings extends BaseBindings {
  private static final Logger logger = LoggerFactory.getLogger(DynamicDistributionBindings.class);

  public static MemorySegment allocate() {
    logger.trace("allocate");
    return LIBRARY_ARENA.allocate(DynamicDistribution.layout());
  }

  public static void tag(MemorySegment dynamicDistributionAddress, long tag) {
    logger.trace("tag - dynamicDistributionAddress: {}, tag: {}", dynamicDistributionAddress, tag);
    DynamicDistribution.tag(dynamicDistributionAddress, tag);
  }

  public static long tag(MemorySegment dynamicDistributionAddress) {
    logger.trace("tag - dynamicDistributionAddress: {}", dynamicDistributionAddress);
    return DynamicDistribution.tag(dynamicDistributionAddress);
  }

  public static void payload(MemorySegment dynamicDistributionAddress, MemorySegment dynamicDistributionPayloadAddress) {
    logger.trace("payload - dynamicDistributionAddress: {}, dynamicDistributionPayloadAddress: {}", dynamicDistributionAddress, dynamicDistributionPayloadAddress);
    DynamicDistribution.distribution(dynamicDistributionAddress, dynamicDistributionPayloadAddress);
  }

  public static MemorySegment payload(MemorySegment dynamicDistributionAddress) {
    logger.trace("payload - dynamicDistributionAddress: {}", dynamicDistributionAddress);
    return DynamicDistribution.distribution(dynamicDistributionAddress);
  }
}
