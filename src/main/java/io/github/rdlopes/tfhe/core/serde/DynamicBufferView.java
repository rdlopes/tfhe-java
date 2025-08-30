package io.github.rdlopes.tfhe.core.serde;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.core.serde.FheSerializable.BUFFER_MAX_SIZE;
import static io.github.rdlopes.tfhe.ffm.DynamicBufferView.layout;

public class DynamicBufferView extends GroupLayoutPointer {

  public DynamicBufferView(MemorySegment pointer, long byteSize) {
    super(layout());
    io.github.rdlopes.tfhe.ffm.DynamicBufferView.pointer(getAddress(), pointer);
    io.github.rdlopes.tfhe.ffm.DynamicBufferView.length(getAddress(), byteSize);
  }

  public static DynamicBufferView fromByteArray(byte[] bytes) {
    MemorySegment dataSegment = ARENA.allocate(bytes.length);
    dataSegment.asByteBuffer()
               .put(bytes);
    return new DynamicBufferView(dataSegment, bytes.length);
  }

  public MemorySegment getPointer() {
    return io.github.rdlopes.tfhe.ffm.DynamicBufferView.pointer(getAddress());
  }

  public long getLength() {
    return io.github.rdlopes.tfhe.ffm.DynamicBufferView.length(getAddress());
  }

  public byte[] toByteArray() {
    if (getLength() > BUFFER_MAX_SIZE) {
      throw new IllegalStateException("Buffer length exceeds maximum allowed size");
    }

    byte[] bytes = new byte[(int) getLength()];
    getPointer()
      .reinterpret(getLength())
      .asByteBuffer()
      .get(bytes);
    return bytes;
  }
}
