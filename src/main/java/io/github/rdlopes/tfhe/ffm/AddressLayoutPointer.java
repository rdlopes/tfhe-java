package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.C_POINTER;

public class AddressLayoutPointer extends MemoryLayoutPointer<AddressLayout> {
  public AddressLayoutPointer() {
    super(ARENA.allocate(C_POINTER), C_POINTER);
  }

  public MemorySegment getValue() {
    return getAddress().get(getLayout(), 0);
  }
}
