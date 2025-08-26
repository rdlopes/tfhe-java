package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.TUniformBindings;

import java.lang.foreign.MemorySegment;

public record TUniform(MemorySegment address) {

  public TUniform(int boundLog2) {
    this(TUniformBindings.allocate());

    TUniformBindings.boundLog2(address, boundLog2);
  }

  public int boundLog2() {
    return TUniformBindings.boundLog2(address);
  }
}
