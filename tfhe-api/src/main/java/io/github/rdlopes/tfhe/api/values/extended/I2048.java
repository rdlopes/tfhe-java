package io.github.rdlopes.tfhe.api.values.extended;

import io.github.rdlopes.tfhe.api.values.AbstractValue;

import io.github.rdlopes.tfhe.api.values.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

@Generated
public final class I2048 extends AbstractValue {
  public static final int BIT_SIZE = 2048;
  public static final BigInteger MIN_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .negate();
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .subtract(ONE);
  
  private I2048(BigInteger value) {
    super(io.github.rdlopes.tfhe.core.ffm.I2048::allocate, BIT_SIZE, true, MIN_VALUE, MAX_VALUE, value);
  }
  
  /// Creates a new zero-initialized instance for use as a native output slot.
  public static I2048 newEmpty() {
    return new I2048(BigInteger.ZERO);
  }

  @NonNull
  public static I2048 of(@NonNull BigInteger value) {
    return new I2048(value);
  }
  
  
  @Override
  protected void setWord(int index, long word) {
    io.github.rdlopes.tfhe.core.ffm.I2048.words(getAddress(), index, word);
  }

  @Override
  protected long getWord(int index) {
    return io.github.rdlopes.tfhe.core.ffm.I2048.words(getAddress(), index);
  }
}
