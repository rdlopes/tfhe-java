package io.github.rdlopes.tfhe.ffm;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.lang.foreign.MemorySegment;
import java.util.Optional;
import java.util.function.Function;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.C_POINTER;

public class NativePointer extends NativeAddress {
  protected NativePointer(@Nullable Function<MemorySegment, Integer> destroyer) {
    super(allocator -> allocator.allocate(C_POINTER), valueDestroyerOf(destroyer));
  }

  public MemorySegment getValue() {
    return valueOf(getAddress());
  }

  public static MemorySegment valueOf(MemorySegment address) {
    return address.get(C_POINTER, 0);
  }

  protected static @NonNull Function<MemorySegment, Integer> valueDestroyerOf(@Nullable Function<MemorySegment, Integer> destroyer) {
    return address -> Optional.ofNullable(destroyer)
                              .map(d -> d.apply(valueOf(address)))
                              .orElse(0);
  }

}
