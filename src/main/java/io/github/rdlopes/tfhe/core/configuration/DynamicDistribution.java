package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

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
    super(TfheWrapper.new_gaussian_from_std_dev(ARENA, stdDev), io.github.rdlopes.tfhe.ffm.DynamicDistribution.layout());
  }

  public DynamicDistribution(int boundLog2) {
    super(TfheWrapper.new_t_uniform(ARENA, boundLog2), io.github.rdlopes.tfhe.ffm.DynamicDistribution.layout());
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
