package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.MemorySegment;
import java.util.function.Consumer;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.C_POINTER;

public class AddressLayoutPointer extends MemoryLayoutPointer<AddressLayout> {
  public AddressLayoutPointer(Consumer<MemorySegment> cleaner) {
    super(ARENA.allocate(C_POINTER), C_POINTER, cleaner);
  }

  public MemorySegment getValue() {
    return getAddress().get(getLayout(), 0);
  }
}
