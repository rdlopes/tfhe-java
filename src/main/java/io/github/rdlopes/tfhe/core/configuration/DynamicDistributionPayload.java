package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

public class DynamicDistributionPayload extends GroupLayoutPointer {

  public DynamicDistributionPayload(MemorySegment address) {
    super(address, io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.layout());
  }

  public DynamicDistributionPayload() {
    super(
      io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.layout());
  }

  public DynamicDistributionPayload(Gaussian gaussian) {
    super(
      io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.layout()
    );
    io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.gaussian(getAddress(), gaussian.getAddress());
  }

  public DynamicDistributionPayload(TUniform tUniform) {
    super(
      io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.layout()
    );
    io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.t_uniform(getAddress(), tUniform.getAddress());
  }

  public Gaussian getGaussian() {
    return new Gaussian(io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.gaussian(getAddress()));
  }

  public void setGaussian(Gaussian gaussian) {
    io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.gaussian(getAddress(), gaussian.getAddress());
  }

  public TUniform getTUniform() {
    return new TUniform(io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.t_uniform(getAddress()));
  }

  public void setTUniform(TUniform tUniform) {
    io.github.rdlopes.tfhe.ffm.DynamicDistributionPayload.t_uniform(getAddress(), tUniform.getAddress());
  }
}
