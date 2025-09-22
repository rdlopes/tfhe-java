package io.github.rdlopes.tfhe.api.types;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

public final class I128 extends AbstractValueHolder {
  public static final int BIT_SIZE = 128;
  public static final BigInteger MIN_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .negate();
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .subtract(ONE);

  public I128() {
    super(io.github.rdlopes.tfhe.ffm.I128::allocate, BIT_SIZE, true, MIN_VALUE, MAX_VALUE);
  }

  @NonNull
  public static I128 valueOf(@NonNull BigInteger value) {
    I128 i128 = new I128();
    i128.setValue(value);
    return i128;
  }

  @Override
  protected void setWord(int index, long word) {
    switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.I128.w0(getAddress(), word);
      case 1 -> io.github.rdlopes.tfhe.ffm.I128.w1(getAddress(), word);
      default -> throw new IndexOutOfBoundsException("Index must be 0 or 1");
    }
  }

  @Override
  protected long getWord(int index) {
    return switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.I128.w0(getAddress());
      case 1 -> io.github.rdlopes.tfhe.ffm.I128.w1(getAddress());
      default -> throw new IndexOutOfBoundsException("Index must be 0 or 1");
    };
  }
}
