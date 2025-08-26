package io.github.rdlopes.tfhe.core.serde;

import io.github.rdlopes.tfhe.ffm.DynamicBufferBindings;

import java.lang.foreign.MemorySegment;

public record DynamicBuffer(MemorySegment address) {

  public DynamicBuffer() {
    this(DynamicBufferBindings.allocate());
  }

  public MemorySegment pointer() {
    return DynamicBufferBindings.pointer(address);
  }

  public void pointer(MemorySegment value) {
    DynamicBufferBindings.pointer(address, value);
  }

  public long length() {
    return DynamicBufferBindings.length(address);
  }

  public void length(long value) {
    DynamicBufferBindings.length(address, value);
  }

  public MemorySegment destructor() {
    return DynamicBufferBindings.destructor(address);
  }

  public void destructor(MemorySegment value) {
    DynamicBufferBindings.destructor(address, value);
  }

  public DynamicBufferView view() {
    DynamicBufferView view = new DynamicBufferView();
    view.pointer(pointer());
    view.length(length());

    return view;
  }

}
