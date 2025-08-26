package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.DynamicDistributionPayloadBindings;

import java.lang.foreign.MemorySegment;

public record DynamicDistributionPayload(MemorySegment pointer) {

  public DynamicDistributionPayload(Gaussian gaussian) {
    this(DynamicDistributionPayloadBindings.allocate());

    DynamicDistributionPayloadBindings.gaussian(pointer, gaussian.pointer());
  }

  public DynamicDistributionPayload(TUniform tUniform) {
    this(DynamicDistributionPayloadBindings.allocate());

    DynamicDistributionPayloadBindings.tUniform(pointer, tUniform.pointer());
  }

  public Gaussian gaussian() {
    return new Gaussian(DynamicDistributionPayloadBindings.gaussian(pointer));
  }

  public TUniform tUniform() {
    return new TUniform(DynamicDistributionPayloadBindings.tUniform(pointer));
  }
}
