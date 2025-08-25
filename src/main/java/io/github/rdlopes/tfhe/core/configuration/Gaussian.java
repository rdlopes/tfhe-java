package io.github.rdlopes.tfhe.core.configuration;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheMemoryAllocator.allocateGaussian;
import static io.github.rdlopes.tfhe.ffm.TfheParameterAccessors.gaussianStd;

public record Gaussian(MemorySegment pointer) {

  public Gaussian(double stdDev) {
    this(allocateGaussian());

    gaussianStd(pointer, stdDev);
  }

  public double std() {
    return gaussianStd(pointer);
  }
}
