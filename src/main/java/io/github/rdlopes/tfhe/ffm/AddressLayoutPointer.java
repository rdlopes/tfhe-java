package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.AddressLayout;
import java.lang.foreign.MemorySegment;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.C_POINTER;

public abstract class AddressLayoutPointer extends MemoryLayoutPointer<AddressLayout> {

  public AddressLayoutPointer(Class<?> clazz, Function<MemorySegment, Integer> destroyer) {
    super(clazz, ARENA.allocate(C_POINTER), C_POINTER, destroyer);
  }

  public AddressLayoutPointer() {
    this(null, null);
  }

  public MemorySegment getValue() {
    return getAddress().get(getLayout(), 0);
  }
}
