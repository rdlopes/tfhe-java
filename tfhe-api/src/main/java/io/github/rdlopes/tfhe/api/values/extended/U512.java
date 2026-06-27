package io.github.rdlopes.tfhe.api.values.extended;

import io.github.rdlopes.tfhe.api.values.AbstractValue;

import io.github.rdlopes.tfhe.api.values.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

@Generated
public final class U512 extends AbstractValue {
  public static final int BIT_SIZE = 512;
  public static final BigInteger MIN_VALUE = ZERO;
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE)
                                                       .subtract(ONE);
  
  private U512(BigInteger value) {
    super(io.github.rdlopes.tfhe.core.ffm.U512::allocate, BIT_SIZE, false, MIN_VALUE, MAX_VALUE, value);
  }
  
  /// Creates a new zero-initialized instance for use as a native output slot.
  public static U512 newEmpty() {
    return new U512(ZERO);
  }

  @NonNull
  public static U512 of(@NonNull BigInteger value) {
    return new U512(value);
  }
  
  
  @Override
  protected void setWord(int index, long word) {
    io.github.rdlopes.tfhe.core.ffm.U512.words(getAddress(), index, word);
  }

  @Override
  protected long getWord(int index) {
    return io.github.rdlopes.tfhe.core.ffm.U512.words(getAddress(), index);
  }
}
