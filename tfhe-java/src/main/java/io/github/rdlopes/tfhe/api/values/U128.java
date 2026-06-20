package io.github.rdlopes.tfhe.api.values;

import io.github.rdlopes.tfhe.utils.Generated;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

@Generated
public final class U128 extends AbstractValue {
  public static final int BIT_SIZE = 128;
  public static final BigInteger MIN_VALUE = ZERO;
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE)
                                                       .subtract(ONE);
  
  private U128(BigInteger value) {
    super(io.github.rdlopes.tfhe.ffm.U128::allocate, BIT_SIZE, false, MIN_VALUE, MAX_VALUE, value);
  }
  
  /// Creates a new zero-initialized instance for use as a native output slot.
  public static U128 newEmpty() {
    return new U128(ZERO);
  }

  @NonNull
  public static U128 of(@NonNull BigInteger value) {
    return new U128(value);
  }
  
  
  @Override
  protected void setWord(int index, long word) {
    switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.U128.w0(getAddress(), word);
      case 1 -> io.github.rdlopes.tfhe.ffm.U128.w1(getAddress(), word);
      default -> throw new IndexOutOfBoundsException("Index must be 0 or 1");
    }
  }

  @Override
  protected long getWord(int index) {
    return switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.U128.w0(getAddress());
      case 1 -> io.github.rdlopes.tfhe.ffm.U128.w1(getAddress());
      default -> throw new IndexOutOfBoundsException("Index must be 0 or 1");
    };
  }
}
