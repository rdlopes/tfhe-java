package io.github.rdlopes.tfhe.api.serde;

import io.github.rdlopes.tfhe.ffm.NativeAddress;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.jspecify.annotations.NonNull;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.function.Function;

public class DynamicBuffer extends NativeAddress implements AutoCloseable {

  public DynamicBuffer() {
    this(io.github.rdlopes.tfhe.ffm.DynamicBuffer::allocate);
  }

  DynamicBuffer(Function<SegmentAllocator, MemorySegment> allocator) {
    super(allocator, TfheHeader::destroy_dynamic_buffer);
  }

  @NonNull
  public static DynamicBuffer fromByteArray(byte[] bytes) {
    return new DynamicBuffer(allocator -> {
      long length = bytes.length;
      MemorySegment pointer = allocator.allocate(length);
      pointer.asByteBuffer()
             .put(bytes);
      MemorySegment newSegment = io.github.rdlopes.tfhe.ffm.DynamicBuffer.allocate(allocator);
      io.github.rdlopes.tfhe.ffm.DynamicBuffer.pointer(newSegment, pointer);
      io.github.rdlopes.tfhe.ffm.DynamicBuffer.length(newSegment, length);
      return newSegment;
    });
  }

  public Long getLength() {
    return io.github.rdlopes.tfhe.ffm.DynamicBuffer.length(getAddress());
  }

  public byte[] toByteArray() {
    if (getLength() > Integer.MAX_VALUE) {
      throw new IllegalArgumentException("Buffer length %s exceeds maximum length %s".formatted(getLength(), Integer.MAX_VALUE));
    }
    byte[] bytes = new byte[getLength().intValue()];
    io.github.rdlopes.tfhe.ffm.DynamicBuffer.pointer(getAddress())
                                            .reinterpret(getLength())
                                            .asByteBuffer()
                                            .get(bytes);
    return bytes;
  }

  @Override
  public void close() {
    destroy();
  }
}
