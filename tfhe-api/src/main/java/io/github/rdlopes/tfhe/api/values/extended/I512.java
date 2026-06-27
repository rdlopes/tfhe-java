package io.github.rdlopes.tfhe.api.values.extended;

import io.github.rdlopes.tfhe.api.values.AbstractValue;

import io.github.rdlopes.tfhe.api.values.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

@Generated
public final class I512 extends AbstractValue {
  public static final int BIT_SIZE = 512;
  public static final BigInteger MIN_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .negate();
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .subtract(ONE);
  
  private I512(BigInteger value) {
    super(io.github.rdlopes.tfhe.core.ffm.I512::allocate, BIT_SIZE, true, MIN_VALUE, MAX_VALUE, value);
  }
  
  /// Creates a new zero-initialized instance for use as a native output slot.
  public static I512 newEmpty() {
    return new I512(BigInteger.ZERO);
  }

  @NonNull
  public static I512 of(@NonNull BigInteger value) {
    return new I512(value);
  }
  
  
  @Override
  protected void setWord(int index, long word) {
    io.github.rdlopes.tfhe.core.ffm.I512.words(getAddress(), index, word);
  }

  @Override
  protected long getWord(int index) {
    return io.github.rdlopes.tfhe.core.ffm.I512.words(getAddress(), index);
  }
}
