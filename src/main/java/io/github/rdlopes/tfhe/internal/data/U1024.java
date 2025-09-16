package io.github.rdlopes.tfhe.internal.data;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public final class U1024 extends DataValue {
  public static final int BIT_SIZE = 1024;
  public static final BigInteger MIN_VALUE = ZERO;
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE)
                                                       .subtract(ONE);

  public U1024() {
    super(io.github.rdlopes.tfhe.ffm.U1024::allocate, BIT_SIZE, false, MIN_VALUE, MAX_VALUE);
  }

  @NonNull
  public static U1024 valueOf(@NonNull BigInteger value) {
    U1024 u1024 = new U1024();
    u1024.setValue(value);
    return u1024;
  }

  @Override
  protected void setWord(int index, long word) {
    io.github.rdlopes.tfhe.ffm.U1024.words(getAddress(), index, word);
  }

  @Override
  protected long getWord(int index) {
    return io.github.rdlopes.tfhe.ffm.U1024.words(getAddress(), index);
  }
}
