package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

public class Gaussian extends GroupLayoutPointer {

  public Gaussian(MemorySegment address) {
    super(address, io.github.rdlopes.tfhe.ffm.Gaussian.layout());
  }


  public Gaussian(double stdDev) {
    super(io.github.rdlopes.tfhe.ffm.Gaussian.layout());
    io.github.rdlopes.tfhe.ffm.Gaussian.std(getAddress(), stdDev);
  }

  public double getStd() {
    return io.github.rdlopes.tfhe.ffm.Gaussian.std(getAddress());
  }
}
