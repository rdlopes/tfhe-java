package io.github.rdlopes.tfhe.internal.data;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public final class U128 extends AbstractIntegerWrapper {
  public static final int BIT_SIZE = 128;
  public static final BigInteger MIN_VALUE = ZERO;
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE)
                                                       .subtract(ONE);

  U128() {
    super(io.github.rdlopes.tfhe.ffm.U128::allocate, BIT_SIZE, false, MIN_VALUE, MAX_VALUE);
  }

  @NonNull
  public static U128 valueOf(@NonNull BigInteger value) {
    U128 u128 = new U128();
    u128.setValue(value);
    return u128;
  }

  @Override
  protected void setWord(int index, long word) {
    switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.U128.w0(getAddress(), word);
      case 1 -> io.github.rdlopes.tfhe.ffm.U128.w1(getAddress(), word);
      default -> throw new IndexOutOfBoundsException("Index must be 0 or 1");
    }
  }

  @Override
  protected long getWord(int index) {
    return switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.U128.w0(getAddress());
      case 1 -> io.github.rdlopes.tfhe.ffm.U128.w1(getAddress());
      default -> throw new IndexOutOfBoundsException("Index must be 0 or 1");
    };
  }
}
