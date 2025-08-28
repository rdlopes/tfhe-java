package io.github.rdlopes.tfhe.core.serde;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.DynamicBufferView.layout;

public class DynamicBufferView extends GroupLayoutPointer {

  public DynamicBufferView(MemorySegment address) {
    super(address, layout());
  }

  public DynamicBufferView() {
    super(layout());
  }

  public static DynamicBufferView fromByteArray(byte[] bytes) {
    DynamicBufferView bufferView = new DynamicBufferView();
    MemorySegment dataSegment = ARENA.allocate(bytes.length);
    dataSegment.asByteBuffer()
               .put(bytes);
    bufferView.setPointer(dataSegment);
    bufferView.setLength(bytes.length);
    return bufferView;
  }

  public MemorySegment getPointer() {
    return io.github.rdlopes.tfhe.ffm.DynamicBufferView.pointer(getAddress());
  }

  public void setPointer(MemorySegment value) {
    io.github.rdlopes.tfhe.ffm.DynamicBufferView.pointer(getAddress(), value);
  }

  public long getLength() {
    return io.github.rdlopes.tfhe.ffm.DynamicBufferView.length(getAddress());
  }

  public void setLength(long value) {
    io.github.rdlopes.tfhe.ffm.DynamicBufferView.length(getAddress(), value);
  }

  public byte[] toByteArray() {
    if (getLength() > Integer.MAX_VALUE) {
      throw new IllegalStateException("The length of the buffer is too big");
    }

    byte[] bytes = new byte[(int) getLength()];
    getPointer()
      .reinterpret(getLength())
      .asByteBuffer()
      .get(bytes);
    return bytes;
  }
}
