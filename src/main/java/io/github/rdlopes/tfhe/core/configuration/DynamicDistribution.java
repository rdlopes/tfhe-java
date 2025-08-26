package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.DynamicDistributionBindings;
import io.github.rdlopes.tfhe.ffm.GaussianBindings;
import io.github.rdlopes.tfhe.ffm.TUniformBindings;

import java.lang.foreign.MemorySegment;

public record DynamicDistribution(MemorySegment address) {

  public DynamicDistribution(long tag, DynamicDistributionPayload distribution) {
    this(DynamicDistributionBindings.allocate());
    DynamicDistributionBindings.tag(address, tag);
    DynamicDistributionBindings.payload(address, distribution.address());
  }

  public DynamicDistribution(double stdDev) {
    this(GaussianBindings.allocateFromStdDev(stdDev));
  }

  public DynamicDistribution(int boundLog2) {
    this(TUniformBindings.allocate(boundLog2));
  }

  public long tag() {
    return DynamicDistributionBindings.tag(address);
  }

  public DynamicDistributionPayload distribution() {
    return new DynamicDistributionPayload(DynamicDistributionBindings.payload(address));
  }
}
