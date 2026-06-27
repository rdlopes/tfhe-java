package io.github.rdlopes.tfhe.api.values.extended;

import io.github.rdlopes.tfhe.api.values.AbstractValue;

import io.github.rdlopes.tfhe.api.values.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

@Generated
public final class U256 extends AbstractValue {
  public static final int BIT_SIZE = 256;
  public static final BigInteger MIN_VALUE = ZERO;
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE)
                                                       .subtract(ONE);
  
  private U256(BigInteger value) {
    super(io.github.rdlopes.tfhe.core.ffm.U256::allocate, BIT_SIZE, false, MIN_VALUE, MAX_VALUE, value);
  }
  
  /// Creates a new zero-initialized instance for use as a native output slot.
  public static U256 newEmpty() {
    return new U256(ZERO);
  }

  @NonNull
  public static U256 of(@NonNull BigInteger value) {
    return new U256(value);
  }
  
  
  @Override
  protected void setWord(int index, long word) {
    switch (index) {
      case 0 -> io.github.rdlopes.tfhe.core.ffm.U256.w0(getAddress(), word);
      case 1 -> io.github.rdlopes.tfhe.core.ffm.U256.w1(getAddress(), word);
      case 2 -> io.github.rdlopes.tfhe.core.ffm.U256.w2(getAddress(), word);
      case 3 -> io.github.rdlopes.tfhe.core.ffm.U256.w3(getAddress(), word);
      default -> throw new IndexOutOfBoundsException("Index must be 0 to 3");
    }
  }

  @Override
  protected long getWord(int index) {
    return switch (index) {
      case 0 -> io.github.rdlopes.tfhe.core.ffm.U256.w0(getAddress());
      case 1 -> io.github.rdlopes.tfhe.core.ffm.U256.w1(getAddress());
      case 2 -> io.github.rdlopes.tfhe.core.ffm.U256.w2(getAddress());
      case 3 -> io.github.rdlopes.tfhe.core.ffm.U256.w3(getAddress());
      default -> throw new IndexOutOfBoundsException("Index must be 0 to 3");
    };
  }
}
