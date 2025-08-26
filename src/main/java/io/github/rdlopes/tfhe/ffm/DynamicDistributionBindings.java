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

  public static void tag(MemorySegment dynamicDistributionPointer, long tag) {
    logger.trace("tag - dynamicDistributionPointer: {}, tag: {}", dynamicDistributionPointer, tag);
    DynamicDistribution.tag(dynamicDistributionPointer, tag);
  }

  public static long tag(MemorySegment dynamicDistributionPointer) {
    logger.trace("tag - dynamicDistributionPointer: {}", dynamicDistributionPointer);
    return DynamicDistribution.tag(dynamicDistributionPointer);
  }

  public static void payload(MemorySegment dynamicDistributionPointer, MemorySegment dynamicDistributionPayloadPointer) {
    logger.trace("payload - dynamicDistributionPointer: {}, dynamicDistributionPayloadPointer: {}", dynamicDistributionPointer, dynamicDistributionPayloadPointer);
    DynamicDistribution.distribution(dynamicDistributionPointer, dynamicDistributionPayloadPointer);
  }

  public static MemorySegment payload(MemorySegment dynamicDistributionPointer) {
    logger.trace("payload - dynamicDistributionPointer: {}", dynamicDistributionPointer);
    return DynamicDistribution.distribution(dynamicDistributionPointer);
  }
}
