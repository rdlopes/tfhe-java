package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

public class TUniform extends GroupLayoutPointer {

  public TUniform(MemorySegment address) {
    super(address, io.github.rdlopes.tfhe.ffm.TUniform.layout());
  }

  public TUniform(int boundLog2) {
    super(
      io.github.rdlopes.tfhe.ffm.TUniform.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.TUniform.layout());
    io.github.rdlopes.tfhe.ffm.TUniform.bound_log2(getAddress(), boundLog2);
  }

  public int getBoundLog2() {
    return io.github.rdlopes.tfhe.ffm.TUniform.bound_log2(getAddress());
  }
}
