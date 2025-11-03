package io.github.rdlopes.tfhe.api.values;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

public final class I256 extends AbstractValue {
  public static final int BIT_SIZE = 256;
  public static final BigInteger MIN_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .negate();
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE - 1)
                                                       .subtract(ONE);

  public I256() {
    super(io.github.rdlopes.tfhe.ffm.I256::allocate, BIT_SIZE, true, MIN_VALUE, MAX_VALUE);
  }

  @NonNull
  public static I256 valueOf(@NonNull BigInteger value) {
    I256 i256 = new I256();
    i256.setValue(value);
    return i256;
  }

  @Override
  protected void setWord(int index, long word) {
    switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.I256.w0(getAddress(), word);
      case 1 -> io.github.rdlopes.tfhe.ffm.I256.w1(getAddress(), word);
      case 2 -> io.github.rdlopes.tfhe.ffm.I256.w2(getAddress(), word);
      case 3 -> io.github.rdlopes.tfhe.ffm.I256.w3(getAddress(), word);
      default -> throw new IndexOutOfBoundsException("Index must be 0 to 3");
    }
  }

  @Override
  protected long getWord(int index) {
    return switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.I256.w0(getAddress());
      case 1 -> io.github.rdlopes.tfhe.ffm.I256.w1(getAddress());
      case 2 -> io.github.rdlopes.tfhe.ffm.I256.w2(getAddress());
      case 3 -> io.github.rdlopes.tfhe.ffm.I256.w3(getAddress());
      default -> throw new IndexOutOfBoundsException("Index must be 0 to 3");
    };
  }
}
