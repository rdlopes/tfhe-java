package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

public class DynamicDistribution extends GroupLayoutPointer {

  public DynamicDistribution(MemorySegment address) {
    super(address, io.github.rdlopes.tfhe.ffm.DynamicDistribution.layout());
  }

  public DynamicDistribution(long tag, DynamicDistributionPayload distribution) {
    super(io.github.rdlopes.tfhe.ffm.DynamicDistribution.layout());
    io.github.rdlopes.tfhe.ffm.DynamicDistribution.tag(getAddress(), tag);
    io.github.rdlopes.tfhe.ffm.DynamicDistribution.distribution(getAddress(), distribution.getAddress());
  }

  public DynamicDistribution(double stdDev) {
    super(io.github.rdlopes.tfhe.ffm.DynamicDistribution.layout());
    Gaussian gaussian = new Gaussian(stdDev);
    DynamicDistributionPayload payload = new DynamicDistributionPayload();
    payload.gaussian(gaussian.getAddress());
    io.github.rdlopes.tfhe.ffm.DynamicDistribution.tag(getAddress(), 0L);
    io.github.rdlopes.tfhe.ffm.DynamicDistribution.distribution(getAddress(), payload.getAddress());
  }

  public DynamicDistribution(int boundLog2) {
    super(io.github.rdlopes.tfhe.ffm.DynamicDistribution.layout());
    TUniform tUniform = new TUniform(boundLog2);
    DynamicDistributionPayload payload = new DynamicDistributionPayload();
    payload.tUniform(tUniform.getAddress());
    io.github.rdlopes.tfhe.ffm.DynamicDistribution.tag(getAddress(), 1L);
    io.github.rdlopes.tfhe.ffm.DynamicDistribution.distribution(getAddress(), payload.getAddress());
  }

  public long getTag() {
    return io.github.rdlopes.tfhe.ffm.DynamicDistribution.tag(getAddress());
  }

  public void setTag(long tag) {
    io.github.rdlopes.tfhe.ffm.DynamicDistribution.tag(getAddress(), tag);
  }

  public DynamicDistributionPayload getDistribution() {
    return new DynamicDistributionPayload(io.github.rdlopes.tfhe.ffm.DynamicDistribution.distribution(getAddress()));
  }

  public void setDistribution(DynamicDistributionPayload distribution) {
    io.github.rdlopes.tfhe.ffm.DynamicDistribution.distribution(getAddress(), distribution.getAddress());
  }
}
