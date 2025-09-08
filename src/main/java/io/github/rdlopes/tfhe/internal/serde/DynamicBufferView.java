package io.github.rdlopes.tfhe.internal.serde;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import org.jspecify.annotations.NonNull;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.util.function.Function;

public class DynamicBufferView extends NativePointer {

  DynamicBufferView(Function<SegmentAllocator, MemorySegment> allocator) {
    super(allocator);
  }

  @NonNull
  public static DynamicBufferView fromByteArray(byte[] bytes) {
    return new DynamicBufferView(allocator -> {
      long length = bytes.length;
      MemorySegment pointer = allocator.allocate(length);
      pointer.asByteBuffer()
             .put(bytes);
      MemorySegment newSegment = io.github.rdlopes.tfhe.ffm.DynamicBufferView.allocate(allocator);
      io.github.rdlopes.tfhe.ffm.DynamicBufferView.pointer(newSegment, pointer);
      io.github.rdlopes.tfhe.ffm.DynamicBufferView.length(newSegment, length);
      return newSegment;
    });
  }

}
