package io.github.rdlopes.tfhe.core.serde;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.DynamicBuffer.layout;
import static io.github.rdlopes.tfhe.ffm.TfheWrapper.destroy_dynamic_buffer;

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
    executeWithErrorHandling(() -> destroy_dynamic_buffer(getPointer()));
  }
}
