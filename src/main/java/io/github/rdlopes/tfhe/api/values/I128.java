package io.github.rdlopes.tfhe.api.values;

import io.github.rdlopes.tfhe.utils.Generated;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

@Generated
public final class I128 extends AbstractValue {
  public static final int BIT_SIZE = 128;
  public static final BigInteger MIN_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .negate();
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .subtract(ONE);
  
  private I128(BigInteger value) {
    super(io.github.rdlopes.tfhe.ffm.I128::allocate, BIT_SIZE, true, MIN_VALUE, MAX_VALUE, value);
  }
  
  /// Creates a new zero-initialized instance for use as a native output slot.
  public static I128 newEmpty() {
    return new I128(BigInteger.ZERO);
  }

  @NonNull
  public static I128 of(@NonNull BigInteger value) {
    return new I128(value);
  }
  
  
  @Override
  protected void setWord(int index, long word) {
    switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.I128.w0(getAddress(), word);
      case 1 -> io.github.rdlopes.tfhe.ffm.I128.w1(getAddress(), word);
      default -> throw new IndexOutOfBoundsException("Index must be 0 or 1");
    }
  }

  @Override
  protected long getWord(int index) {
    return switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.I128.w0(getAddress());
      case 1 -> io.github.rdlopes.tfhe.ffm.I128.w1(getAddress());
      default -> throw new IndexOutOfBoundsException("Index must be 0 or 1");
    };
  }
}
