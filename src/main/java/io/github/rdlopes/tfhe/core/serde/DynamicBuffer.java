package io.github.rdlopes.tfhe.core.serde;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.DynamicBuffer.layout;

public class DynamicBuffer extends GroupLayoutPointer {

  public DynamicBuffer() {
    super(layout());
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
    return new DynamicBufferView(getPointer(), getLength());
  }

  public void destroy() {
    // FIXME: crashes VM
    // executeWithErrorHandling(() -> destroy_dynamic_buffer(getPointer()));
  }
}
