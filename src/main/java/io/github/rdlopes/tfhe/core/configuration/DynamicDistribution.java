package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.DynamicDistributionBindings;
import io.github.rdlopes.tfhe.ffm.GaussianBindings;
import io.github.rdlopes.tfhe.ffm.TUniformBindings;

import java.lang.foreign.MemorySegment;

public record DynamicDistribution(MemorySegment pointer) {

  public DynamicDistribution(long tag, DynamicDistributionPayload distribution) {
    this(DynamicDistributionBindings.allocate());
    DynamicDistributionBindings.tag(pointer, tag);
    DynamicDistributionBindings.payload(pointer, distribution.pointer());
  }

  public DynamicDistribution(double stdDev) {
    this(GaussianBindings.allocateFromStdDev(stdDev));
  }

  public DynamicDistribution(int boundLog2) {
    this(TUniformBindings.allocate(boundLog2));
  }

  public long tag() {
    return DynamicDistributionBindings.tag(pointer);
  }

  public DynamicDistributionPayload distribution() {
    return new DynamicDistributionPayload(DynamicDistributionBindings.payload(pointer));
  }
}
