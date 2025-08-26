package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.DynamicDistributionPayloadBindings;

import java.lang.foreign.MemorySegment;

public record DynamicDistributionPayload(MemorySegment address) {

  public DynamicDistributionPayload(Gaussian gaussian) {
    this(DynamicDistributionPayloadBindings.allocate());

    DynamicDistributionPayloadBindings.gaussian(address, gaussian.address());
  }

  public DynamicDistributionPayload(TUniform tUniform) {
    this(DynamicDistributionPayloadBindings.allocate());

    DynamicDistributionPayloadBindings.tUniform(address, tUniform.address());
  }

  public Gaussian gaussian() {
    return new Gaussian(DynamicDistributionPayloadBindings.gaussian(address));
  }

  public TUniform tUniform() {
    return new TUniform(DynamicDistributionPayloadBindings.tUniform(address));
  }
}
