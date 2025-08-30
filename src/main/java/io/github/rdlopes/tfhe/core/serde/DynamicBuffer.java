package io.github.rdlopes.tfhe.core.serde;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.DynamicBuffer.layout;

public class DynamicBuffer extends GroupLayoutPointer {

  public DynamicBuffer() {
    super(layout(), address -> TfheWrapper.destroy_dynamic_buffer(io.github.rdlopes.tfhe.ffm.DynamicBuffer.pointer(address)));
  }

  public MemorySegment getPointer() {
    return io.github.rdlopes.tfhe.ffm.DynamicBuffer.pointer(getAddress());
  }

  public long getLength() {
    return io.github.rdlopes.tfhe.ffm.DynamicBuffer.length(getAddress());
  }

  public MemorySegment getDestructor() {
    return io.github.rdlopes.tfhe.ffm.DynamicBuffer.destructor(getAddress());
  }

  public DynamicBufferView view() {
    long length = getLength();
    MemorySegment originalPointer = getPointer();
    MemorySegment copiedSegment = ARENA.allocate(length);
    copiedSegment.copyFrom(originalPointer.reinterpret(length));
    return new DynamicBufferView(copiedSegment, length);
  }
}
