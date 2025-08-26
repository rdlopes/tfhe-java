package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GaussianBindings;

import java.lang.foreign.MemorySegment;

public record Gaussian(MemorySegment pointer) {

  public Gaussian(double stdDev) {
    this(GaussianBindings.allocate());

    GaussianBindings.std(pointer, stdDev);
  }

  public double std() {
    return GaussianBindings.std(pointer);
  }
}
