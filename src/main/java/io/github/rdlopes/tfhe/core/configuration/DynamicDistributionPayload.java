package io.github.rdlopes.tfhe.core.configuration;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheMemoryAllocator.allocateDynamicDistributionPayload;
import static io.github.rdlopes.tfhe.ffm.TfheParameterAccessors.dynamicDistributionPayloadGaussian;
import static io.github.rdlopes.tfhe.ffm.TfheParameterAccessors.dynamicDistributionPayloadTUniform;

public record DynamicDistributionPayload(MemorySegment pointer) {

  public DynamicDistributionPayload(Gaussian gaussian) {
    this(allocateDynamicDistributionPayload());

    dynamicDistributionPayloadGaussian(pointer, gaussian.pointer());
  }

  public DynamicDistributionPayload(TUniform tUniform) {
    this(allocateDynamicDistributionPayload());

    dynamicDistributionPayloadTUniform(pointer, tUniform.pointer());
  }

  public Gaussian gaussian() {
    return new Gaussian(dynamicDistributionPayloadGaussian(pointer));
  }

  public TUniform tUniform() {
    return new TUniform(dynamicDistributionPayloadTUniform(pointer));
  }
}
