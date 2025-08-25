package io.github.rdlopes.tfhe.core.configuration;


import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheMemoryAllocator.*;
import static io.github.rdlopes.tfhe.ffm.TfheParameterAccessors.dynamicDistributionPayload;
import static io.github.rdlopes.tfhe.ffm.TfheParameterAccessors.dynamicDistributionTag;

public record DynamicDistribution(MemorySegment pointer) {

  public DynamicDistribution(long tag, DynamicDistributionPayload distribution) {
    this(allocateDynamicDistribution());
    dynamicDistributionTag(pointer, tag);
    dynamicDistributionPayload(pointer, distribution.pointer());
  }

  public DynamicDistribution(double stdDev) {
    this(allocateGaussianFromStdDev(stdDev));
  }

  public DynamicDistribution(int boundLog2) {
    this(allocateTUniform(boundLog2));
  }

  public long tag() {
    return dynamicDistributionTag(pointer);
  }

  public DynamicDistributionPayload distribution() {
    return new DynamicDistributionPayload(dynamicDistributionPayload(pointer));
  }
}
