package io.github.rdlopes.tfhe.api.values.extended;

import io.github.rdlopes.tfhe.api.values.AbstractValue;

import io.github.rdlopes.tfhe.api.values.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

@Generated
public final class I1024 extends AbstractValue {
  public static final int BIT_SIZE = 1024;
  public static final BigInteger MIN_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .negate();
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .subtract(ONE);
  
  private I1024(BigInteger value) {
    super(io.github.rdlopes.tfhe.core.ffm.I1024::allocate, BIT_SIZE, true, MIN_VALUE, MAX_VALUE, value);
  }
  
  /// Creates a new zero-initialized instance for use as a native output slot.
  public static I1024 newEmpty() {
    return new I1024(BigInteger.ZERO);
  }

  @NonNull
  public static I1024 of(@NonNull BigInteger value) {
    return new I1024(value);
  }
  
  
  @Override
  protected void setWord(int index, long word) {
    io.github.rdlopes.tfhe.core.ffm.I1024.words(getAddress(), index, word);
  }

  @Override
  protected long getWord(int index) {
    return io.github.rdlopes.tfhe.core.ffm.I1024.words(getAddress(), index);
  }
}
