package io.github.rdlopes.tfhe.ffm;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.lang.foreign.MemorySegment;
import java.util.Optional;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.C_POINTER;

public class NativePointer extends NativeAddress {
  protected NativePointer(@Nullable Function<MemorySegment, Integer> destroyer) {
    super(allocator -> allocator.allocate(C_POINTER), valueDestroyerOf(destroyer));
  }

  public MemorySegment getValue() {
    return getAddress().get(C_POINTER, 0);
  }

  protected static @NonNull Function<MemorySegment, Integer> valueDestroyerOf(@Nullable Function<MemorySegment, Integer> destroyer) {
    return address -> Optional.ofNullable(destroyer)
                              .map(d -> d.apply(address.get(C_POINTER, 0)))
                              .orElse(0);
  }

}
