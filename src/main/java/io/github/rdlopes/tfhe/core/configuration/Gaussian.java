package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GaussianBindings;

import java.lang.foreign.MemorySegment;

public record Gaussian(MemorySegment address) {

  public Gaussian(double stdDev) {
    this(GaussianBindings.allocate());

    GaussianBindings.std(address, stdDev);
  }

  public double std() {
    return GaussianBindings.std(address);
  }
}
