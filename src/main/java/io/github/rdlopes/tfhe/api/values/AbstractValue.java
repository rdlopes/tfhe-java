package io.github.rdlopes.tfhe.api.values;

import io.github.rdlopes.tfhe.ffm.NativeAddress;
import org.jspecify.annotations.NonNull;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.math.BigInteger;
import java.util.function.Function;

import static java.math.BigInteger.ONE;

/// Abstract base for all wide FHE cleartext value types (`U128` … `I2048`).
///
/// Instances are immutable once constructed. The `initialValue` is written to
/// native memory during construction; after that the memory region is read-only through
/// this API (no `setValue` method is exposed).
///
/// This object owns the native memory (no destroyer — the allocator writes
/// into a LIBRARY_ARENA-backed segment that lives for the duration of the process).
public abstract class AbstractValue extends NativeAddress implements FheValue {
  
  /// Bitmask to isolate the lower 64 bits of a BigInteger word.
  public static final BigInteger LONG_MASK = ONE.shiftLeft(Long.SIZE).subtract(ONE);

  private final int bitSize;
  private final boolean signed;
  private final BigInteger minValue;
  private final BigInteger maxValue;
  
  /// Constructs a new value instance and writes `initialValue` into native memory.
  ///
  /// @param allocator    the FFM allocator for the native struct
  /// @param bitSize      the bit width (128, 256, …, 2048)
  /// @param signed       `true` if this is a signed two's-complement type
  /// @param minValue     the minimum representable value (used for range validation)
  /// @param maxValue     the maximum representable value (used for range validation)
  /// @param initialValue the value to store; must be within {minValue, maxValue}
  /// @throws IllegalArgumentException if `initialValue` is out of range
  AbstractValue(
    Function<SegmentAllocator, MemorySegment> allocator,
    int bitSize,
    boolean signed,
    @NonNull BigInteger minValue,
    @NonNull BigInteger maxValue,
    @NonNull BigInteger initialValue) {
    super(allocator, null);
    this.bitSize = bitSize;
    this.signed = signed;
    this.minValue = minValue;
    this.maxValue = maxValue;
    writeValue(initialValue);
  }

  @NonNull
  @Override
  public BigInteger asBigInteger() {
    BigInteger result = BigInteger.ZERO;
    int wordCount = bitSize / Long.SIZE;
    for (int i = wordCount - 1; i >= 0; i--) {
      long word = getWord(i);
      result = result.shiftLeft(Long.SIZE)
                     .or(BigInteger.valueOf(word).and(LONG_MASK));
    }
    if (signed) {
      int signBitPosition = bitSize - 1;
      BigInteger signBit = ONE.shiftLeft(bitSize);
      return result.testBit(signBitPosition) ? result.subtract(signBit) : result;
    }
    return result;
  }
  
  /// Writes a validated value into the native memory backing this instance.
  /// Called only from the constructor.
  private void writeValue(@NonNull BigInteger value) {
    if (value.compareTo(minValue) < 0) {
      throw new IllegalArgumentException("Value %s below minimum (%s)".formatted(value, minValue));
    }
    if (value.compareTo(maxValue) > 0) {
      throw new IllegalArgumentException("Value %s above maximum (%s)".formatted(value, maxValue));
    }
    BigInteger valueStored = value;
    if (signed) {
      int signBitPosition = bitSize - 1;
      BigInteger signBit = ONE.shiftLeft(bitSize);
      valueStored = value.testBit(signBitPosition) ? value.add(signBit) : value;
    }
    int wordCount = bitSize / Long.SIZE;
    for (int i = 0; i < wordCount; i++) {
      long word = valueStored.shiftRight(Long.SIZE * i).and(LONG_MASK).longValue();
      setWord(i, word);
    }
  }
  
  /// Sets a specific 64-bit word of the native representation at the given index.
  protected abstract void setWord(int index, long word);
  
  /// Reads a specific 64-bit word of the native representation at the given index.
  protected abstract long getWord(int index);
}
