package io.github.rdlopes.tfhe.api.values;

import io.github.rdlopes.tfhe.api.FheValueHolder;
import io.github.rdlopes.tfhe.ffm.NativeAddress;
import org.jspecify.annotations.NonNull;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.math.BigInteger;
import java.util.function.Function;

import static java.math.BigInteger.ONE;

public abstract class AbstractValueHolder extends NativeAddress implements FheValueHolder {

  public static final BigInteger LONG_MASK = ONE.shiftLeft(Long.SIZE)
                                                .subtract(ONE);

  private final int bitSize;
  private final boolean signed;
  private final BigInteger minValue;
  private final BigInteger maxValue;

  AbstractValueHolder(
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
  @Override
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

  @Override
  public void setValue(@NonNull BigInteger value) {
    if (value.compareTo(minValue) < 0) {
      throw new IllegalArgumentException("Value %s below minimum (%s)".formatted(value, minValue));
    }
    if (value.compareTo(maxValue) > 0) {
      throw new IllegalArgumentException("Value %s above maximum (%s)".formatted(value, maxValue));
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
