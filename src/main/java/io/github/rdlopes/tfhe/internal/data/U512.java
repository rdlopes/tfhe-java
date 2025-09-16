package io.github.rdlopes.tfhe.internal.data;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public final class U512 extends DataValue {
  public static final int BIT_SIZE = 512;
  public static final BigInteger MIN_VALUE = ZERO;
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE)
                                                       .subtract(ONE);

  public U512() {
    super(io.github.rdlopes.tfhe.ffm.U512::allocate, BIT_SIZE, false, MIN_VALUE, MAX_VALUE);
  }

  @NonNull
  public static U512 valueOf(@NonNull BigInteger value) {
    U512 u512 = new U512();
    u512.setValue(value);
    return u512;
  }

  @Override
  protected void setWord(int index, long word) {
    io.github.rdlopes.tfhe.ffm.U512.words(getAddress(), index, word);
  }

  @Override
  protected long getWord(int index) {
    return io.github.rdlopes.tfhe.ffm.U512.words(getAddress(), index);
  }
}
