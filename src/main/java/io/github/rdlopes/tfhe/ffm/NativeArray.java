package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.MemorySegment;
import java.util.Collection;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.C_POINTER;

public class NativeArray extends NativeAddress {

  private final long size;

  protected NativeArray(Collection<? extends NativePointer> addresses) {
    super(allocator -> allocator.allocate(C_POINTER, addresses.size()), null);
    this.size = addresses.size();
    long index = 0;
    for (NativePointer address : addresses) {
      getAddress().set(C_POINTER, index * C_POINTER.byteSize(), address.getValue());
      index++;
    }
  }

  public MemorySegment getValue(long index) {
    return valueOf(getAddress(), index);
  }

  public static MemorySegment valueOf(MemorySegment address, long index) {
    return address.get(C_POINTER, index * C_POINTER.byteSize());
  }

  public long getSize() {
    return size;
  }
}
