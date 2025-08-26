package io.github.rdlopes.tfhe.core.serde;

import io.github.rdlopes.tfhe.ffm.DynamicBufferViewBindings;

import java.lang.foreign.MemorySegment;

public record DynamicBufferView(MemorySegment address) {

  public DynamicBufferView() {
    this(DynamicBufferViewBindings.allocate());
  }

  public MemorySegment pointer() {
    return DynamicBufferViewBindings.pointer(address);
  }

  public void pointer(MemorySegment value) {
    DynamicBufferViewBindings.pointer(address, value);
  }

  public long length() {
    return DynamicBufferViewBindings.length(address);
  }

  public void length(long value) {
    DynamicBufferViewBindings.length(address, value);
  }

  public static DynamicBufferView fromByteArray(byte[] bytes) {
    DynamicBufferView bufferView = new DynamicBufferView();
    MemorySegment dataSegment = DynamicBufferViewBindings.allocateData(bytes.length);
    dataSegment.asByteBuffer()
               .put(bytes);
    bufferView.pointer(dataSegment);
    bufferView.length(bytes.length);
    return bufferView;
  }

  public byte[] toByteArray() {
    byte[] bytes = new byte[(int) length()];
    pointer()
      .reinterpret(length())
      .asByteBuffer()
      .get(bytes);
    return bytes;
  }
}
