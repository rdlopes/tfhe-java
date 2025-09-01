package io.github.rdlopes.tfhe.core.serde;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import java.lang.foreign.MemorySegment;

public class DynamicBuffer extends GroupLayoutPointer {

  public DynamicBuffer() {
    super(
      DynamicBuffer.class,
      io.github.rdlopes.tfhe.ffm.DynamicBuffer.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.DynamicBuffer.layout(),
      TfheWrapper::destroy_dynamic_buffer);
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
    MemorySegment dataSegment = getPointer().reinterpret(getLength());
    MemorySegment newSegment = ARENA.allocate(getLength());
    newSegment.copyFrom(dataSegment);
    return new DynamicBufferView(newSegment, getLength());
  }

  public void destroy() {
    super.cleanNativeResources();
  }
}
