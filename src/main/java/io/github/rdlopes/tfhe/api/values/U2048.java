package io.github.rdlopes.tfhe.api.values;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public final class U2048 extends AbstractValue {
  public static final int BIT_SIZE = 2048;
  public static final BigInteger MIN_VALUE = ZERO;
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE)
                                                       .subtract(ONE);
  
  private U2048(BigInteger value) {
    super(io.github.rdlopes.tfhe.ffm.U2048::allocate, BIT_SIZE, false, MIN_VALUE, MAX_VALUE, value);
  }
  
  /// Creates a new zero-initialized instance for use as a native output slot.
  public static U2048 newEmpty() {
    return new U2048(BigInteger.ZERO);
  }

  @NonNull
  public static U2048 of(@NonNull BigInteger value) {
    return new U2048(value);
  }
  
  
  @Override
  protected void setWord(int index, long word) {
    io.github.rdlopes.tfhe.ffm.U2048.words(getAddress(), index, word);
  }

  @Override
  protected long getWord(int index) {
    return io.github.rdlopes.tfhe.ffm.U2048.words(getAddress(), index);
  }
}
