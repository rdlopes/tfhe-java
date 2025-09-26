package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.C_POINTER;

public class NativeArray extends NativeAddress {

  private final long size;

  protected NativeArray(long size) {
    super(allocator -> allocator.allocate(C_POINTER, size), null);
    this.size = size;
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
