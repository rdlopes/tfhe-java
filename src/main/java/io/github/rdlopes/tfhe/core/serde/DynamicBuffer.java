package io.github.rdlopes.tfhe.core.serde;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.DynamicBuffer.layout;

public class DynamicBuffer extends GroupLayoutPointer {

  public DynamicBuffer(MemorySegment address) {
    super(address, layout());
  }

  public DynamicBuffer() {
    super(layout());
  }

  public MemorySegment getPointer() {
    return io.github.rdlopes.tfhe.ffm.DynamicBuffer.pointer(getAddress());
  }

  public void setPointer(MemorySegment value) {
    io.github.rdlopes.tfhe.ffm.DynamicBuffer.pointer(getAddress(), value);
  }

  public long getLength() {
    return io.github.rdlopes.tfhe.ffm.DynamicBuffer.length(getAddress());
  }

  public void setLength(long value) {
    io.github.rdlopes.tfhe.ffm.DynamicBuffer.length(getAddress(), value);
  }

  public MemorySegment getDestructor() {
    return io.github.rdlopes.tfhe.ffm.DynamicBuffer.destructor(getAddress());
  }

  public void setDestructor(MemorySegment value) {
    io.github.rdlopes.tfhe.ffm.DynamicBuffer.destructor(getAddress(), value);
  }

  public DynamicBufferView view() {
    DynamicBufferView view = new DynamicBufferView();
    view.setPointer(getPointer());
    view.setLength(getLength());

    return view;
  }

}
