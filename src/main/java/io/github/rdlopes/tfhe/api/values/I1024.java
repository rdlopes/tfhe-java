package io.github.rdlopes.tfhe.api.values;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

public final class I1024 extends AbstractValueHolder {
  public static final int BIT_SIZE = 1024;
  public static final BigInteger MIN_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .negate();
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .subtract(ONE);

  public I1024() {
    super(io.github.rdlopes.tfhe.ffm.I1024::allocate, BIT_SIZE, true, MIN_VALUE, MAX_VALUE);
  }

  @NonNull
  public static I1024 valueOf(@NonNull BigInteger value) {
    I1024 i1024 = new I1024();
    i1024.setValue(value);
    return i1024;
  }

  @Override
  protected void setWord(int index, long word) {
    io.github.rdlopes.tfhe.ffm.I1024.words(getAddress(), index, word);
  }

  @Override
  protected long getWord(int index) {
    return io.github.rdlopes.tfhe.ffm.I1024.words(getAddress(), index);
  }
}
