package io.github.rdlopes.tfhe.internal.data;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import org.jspecify.annotations.NonNull;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.math.BigInteger;
import java.util.function.Function;

import static java.math.BigInteger.ONE;

abstract sealed class AbstractIntegerWrapper extends NativePointer
  permits I128, U128, I256, U256, I512, U512, I1024, U1024, I2048, U2048 {

  public static final BigInteger LONG_MASK = ONE.shiftLeft(Long.SIZE)
                                                .subtract(ONE);

  private final int bitSize;
  private final boolean signed;
  private final BigInteger minValue;
  private final BigInteger maxValue;

  AbstractIntegerWrapper(
    Function<SegmentAllocator, MemorySegment> allocator,
    int bitSize,
    boolean signed,
    @NonNull BigInteger minValue,
    @NonNull BigInteger maxValue) {
    super(allocator, null);
    this.bitSize = bitSize;
    this.signed = signed;
    this.minValue = minValue;
    this.maxValue = maxValue;
  }

  @NonNull
  public BigInteger getValue() {
    BigInteger result = BigInteger.ZERO;
    int wordCount = bitSize / Long.SIZE;
    for (int i = wordCount - 1; i >= 0; i--) {
      long word = getWord(i);
      result = result.shiftLeft(Long.SIZE)
                     .or(BigInteger.valueOf(word)
                                   .and(LONG_MASK));
    }
    if (signed) {
      int signBitPosition = bitSize - 1;
      BigInteger signBit = BigInteger.ONE.shiftLeft(bitSize);
      return result.testBit(signBitPosition) ? result.subtract(signBit) : result;
    }
    return result;
  }

  protected void setValue(@NonNull BigInteger value) {
    if (value.compareTo(minValue) < 0) {
      throw new IllegalArgumentException("Value below minimum capacity (%s)".formatted(minValue));
    }
    if (value.compareTo(maxValue) > 0) {
      throw new IllegalArgumentException("Value above maximum capacity (%s)".formatted(maxValue));
    }
    BigInteger valueStored = value;
    if (signed) {
      int signBitPosition = bitSize - 1;
      BigInteger signBit = BigInteger.ONE.shiftLeft(bitSize);
      valueStored = value.testBit(signBitPosition) ? value.add(signBit) : value;
    }
    int wordCount = bitSize / Long.SIZE;
    for (int i = 0; i < wordCount; i++) {
      long word = valueStored.shiftRight(Long.SIZE * i)
                             .and(LONG_MASK)
                             .longValue();
      setWord(i, word);
    }
  }

  protected abstract void setWord(int index, long word);

  protected abstract long getWord(int index);

}
