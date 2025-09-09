package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativePointer;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.new_gaussian_from_std_dev;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.new_t_uniform;

public class DynamicDistribution extends NativePointer {

  public DynamicDistribution(Function<SegmentAllocator, MemorySegment> allocator) {
    super(allocator);
  }

  public static DynamicDistribution gaussian(double stdDev) {
    return new DynamicDistribution(allocator -> new_gaussian_from_std_dev(allocator, stdDev));
  }

  public static DynamicDistribution tUniform(int boundLog2) {
    return new DynamicDistribution(allocator -> new_t_uniform(allocator, boundLog2));
  }

}
