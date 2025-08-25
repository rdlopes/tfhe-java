package io.github.rdlopes.tfhe.core.configuration;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheMemoryAllocator.allocateTUniform;
import static io.github.rdlopes.tfhe.ffm.TfheParameterAccessors.tUniformBoundLog2;

public record TUniform(MemorySegment pointer) {

  public TUniform(int boundLog2) {
    this(allocateTUniform());

    tUniformBoundLog2(pointer, boundLog2);
  }

  public int boundLog2() {
    return tUniformBoundLog2(pointer);
  }
}
