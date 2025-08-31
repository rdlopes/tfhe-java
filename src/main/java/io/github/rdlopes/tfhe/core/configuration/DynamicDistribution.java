package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.new_gaussian_from_std_dev;
import static io.github.rdlopes.tfhe.ffm.TfheWrapper.new_t_uniform;

public class DynamicDistribution extends GroupLayoutPointer {

  public DynamicDistribution(MemorySegment address) {
    super(address, io.github.rdlopes.tfhe.ffm.DynamicDistribution.layout());
  }

  public DynamicDistribution(long tag, DynamicDistributionPayload distribution) {
    super(
      io.github.rdlopes.tfhe.ffm.DynamicDistribution.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.DynamicDistribution.layout()
    );
    setTag(tag);
    setDistribution(distribution);
  }

  public static DynamicDistribution gaussian(double stdDev) {
    return new DynamicDistribution(new_gaussian_from_std_dev(ARENA, stdDev));
  }

  public static DynamicDistribution tUniform(int boundLog2) {
    return new DynamicDistribution(new_t_uniform(ARENA, boundLog2));
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
