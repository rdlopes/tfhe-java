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

  public U2048() {
    super(io.github.rdlopes.tfhe.ffm.U2048::allocate, BIT_SIZE, false, MIN_VALUE, MAX_VALUE);
  }

  @NonNull
  public static U2048 valueOf(@NonNull BigInteger value) {
    U2048 u2048 = new U2048();
    u2048.setValue(value);
    return u2048;
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
