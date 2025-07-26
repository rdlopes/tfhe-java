package io.github.rdlopes.tfhe.api;

import ai.zama.tfhe.DynamicBuffer;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.function.Function;
import java.util.function.Supplier;

import static ai.zama.tfhe.TfheNative.*;

public class FfmWrapper {
  private final Arena arena;
  private final MemorySegment pointer;

  public FfmWrapper(Arena arena, MemorySegment pointer) {
    this.arena = arena;
    this.pointer = pointer;
  }

  public Arena arena() {
    return arena;
  }

  public MemorySegment pointer() {
    return pointer;
  }

  public MemorySegment value() {
    return pointer.get(C_POINTER, 0);
  }

  protected void executeAndCheckError(Supplier<Integer> ffmCall) {
    tfhe_error_clear();
    int callResult = ffmCall.get();
    if (callResult != 0) {
      MemorySegment errorPointer = tfhe_error_get_last();
      long errorSize = tfhe_error_get_size();
      byte[] errorBytes = new byte[(int) errorSize];
      MemorySegment errorBytesSegment = MemorySegment.ofArray(errorBytes);
      MemorySegment.copy(errorPointer, 0, errorBytesSegment, 0, errorSize);
      throw new RuntimeException(new String(errorBytes));
    }
  }

  protected byte[] copyBytesFrom(Function<MemorySegment, Integer> ffmCall) {
    MemorySegment dynamicBuffer = DynamicBuffer.allocate(arena());
    executeAndCheckError(() -> ffmCall.apply(dynamicBuffer));

    MemorySegment dynamicBufferPointer = DynamicBuffer.pointer(dynamicBuffer);
    long dynamicBufferLength = DynamicBuffer.length(dynamicBuffer);

    byte[] dynamicBufferBytes = new byte[(int) dynamicBufferLength];
    MemorySegment dynamicBufferBytesSegment = MemorySegment.ofArray(dynamicBufferBytes);
    MemorySegment.copy(dynamicBufferPointer, 0, dynamicBufferBytesSegment, 0, dynamicBufferLength);

    return dynamicBufferBytes;

  }
}
