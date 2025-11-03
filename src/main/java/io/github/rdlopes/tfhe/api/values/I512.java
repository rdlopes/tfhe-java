package io.github.rdlopes.tfhe.api.values;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

public final class I512 extends AbstractValue {
  public static final int BIT_SIZE = 512;
  public static final BigInteger MIN_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .negate();
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .subtract(ONE);

  public I512() {
    super(io.github.rdlopes.tfhe.ffm.I512::allocate, BIT_SIZE, true, MIN_VALUE, MAX_VALUE);
  }

  @NonNull
  public static I512 valueOf(@NonNull BigInteger value) {
    I512 i512 = new I512();
    i512.setValue(value);
    return i512;
  }

  @Override
  protected void setWord(int index, long word) {
    io.github.rdlopes.tfhe.ffm.I512.words(getAddress(), index, word);
  }

  @Override
  protected long getWord(int index) {
    return io.github.rdlopes.tfhe.ffm.I512.words(getAddress(), index);
  }
}
