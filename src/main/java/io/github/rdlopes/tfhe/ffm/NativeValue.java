package io.github.rdlopes.tfhe.ffm;

import org.jspecify.annotations.Nullable;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.C_POINTER;

public class NativeValue extends NativePointer {
  protected NativeValue(@Nullable Function<MemorySegment, Integer> destroyer) {
    super(allocator -> allocator.allocate(C_POINTER), valueDestroyerOf(destroyer));
  }

  public MemorySegment getValue() {
    return getAddress().get(C_POINTER, 0);
  }
}
