package io.github.rdlopes.tfhe.api.types;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

public final class I2048 extends AbstractValueHolder {
  public static final int BIT_SIZE = 2048;
  public static final BigInteger MIN_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .negate();
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .subtract(ONE);

  public I2048() {
    super(io.github.rdlopes.tfhe.ffm.I2048::allocate, BIT_SIZE, true, MIN_VALUE, MAX_VALUE);
  }

  @NonNull
  public static I2048 valueOf(@NonNull BigInteger value) {
    I2048 i2048 = new I2048();
    i2048.setValue(value);
    return i2048;
  }

  @Override
  protected void setWord(int index, long word) {
    io.github.rdlopes.tfhe.ffm.I2048.words(getAddress(), index, word);
  }

  @Override
  protected long getWord(int index) {
    return io.github.rdlopes.tfhe.ffm.I2048.words(getAddress(), index);
  }
}
