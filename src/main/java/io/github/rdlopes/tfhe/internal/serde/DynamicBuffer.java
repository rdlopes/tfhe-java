package io.github.rdlopes.tfhe.internal.serde;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

public class DynamicBuffer extends NativePointer implements AutoCloseable {

  public DynamicBuffer() {
    super(io.github.rdlopes.tfhe.ffm.DynamicBuffer::allocate, TfheHeader::destroy_dynamic_buffer);
  }

  public byte[] toByteArray() {
    long length = io.github.rdlopes.tfhe.ffm.DynamicBuffer.length(getAddress());
    if (length > Integer.MAX_VALUE) {
      throw new IllegalArgumentException("Buffer length %s exceeds maximum length %s".formatted(length, Integer.MAX_VALUE));
    }
    byte[] bytes = new byte[(int) length];
    io.github.rdlopes.tfhe.ffm.DynamicBuffer.pointer(getAddress())
                                            .reinterpret(length)
                                            .asByteBuffer()
                                            .get(bytes);
    return bytes;
  }

  @Override
  public void close() {
    destroy();
  }
}
