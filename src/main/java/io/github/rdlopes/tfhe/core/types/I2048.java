package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.lang.foreign.MemorySegment;
import java.math.BigInteger;
import java.util.Arrays;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.I2048_big_endian_bytes;
import static io.github.rdlopes.tfhe.ffm.TfheWrapper.I2048_from_big_endian_bytes;

public class I2048 extends GroupLayoutPointer {

  protected I2048() {
    super(
      io.github.rdlopes.tfhe.ffm.I2048.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.I2048.layout()
    );
  }

  public static I2048 valueOf(long value) {
    return valueOf(BigInteger.valueOf(value));
  }

  public static I2048 valueOf(String value) {
    return valueOf(new BigInteger(value));
  }

  public static I2048 valueOf(BigInteger value) {
    byte[] bytes = value.toByteArray();

    byte[] paddedBytes = new byte[256];
    if (value.signum() < 0) {
      Arrays.fill(paddedBytes, (byte) 0xFF);
    }

    int offset = paddedBytes.length - bytes.length;
    System.arraycopy(bytes, 0, paddedBytes, offset, bytes.length);

    MemorySegment byteBuffer = ARENA.allocate(256);
    byteBuffer.copyFrom(MemorySegment.ofArray(paddedBytes));

    I2048 i2048 = new I2048();
    executeWithErrorHandling(() -> I2048_from_big_endian_bytes(byteBuffer, 256, i2048.getAddress()));
    return i2048;
  }

  public BigInteger getValue() {
    MemorySegment byteBuffer = ARENA.allocate(256);
    executeWithErrorHandling(() -> I2048_big_endian_bytes(getAddress(), byteBuffer, 256));

    byte[] bytes = new byte[256];
    MemorySegment.ofArray(bytes)
                 .copyFrom(byteBuffer);

    return new BigInteger(bytes);
  }
}
