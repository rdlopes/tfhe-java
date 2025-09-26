package io.github.rdlopes.tfhe.api.values;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;

public final class U256 extends AbstractValueHolder {
  public static final int BIT_SIZE = 256;
  public static final BigInteger MIN_VALUE = ZERO;
  public static final BigInteger MAX_VALUE = BigInteger.valueOf(2)
                                                       .pow(BIT_SIZE)
                                                       .subtract(ONE);

  public U256() {
    super(io.github.rdlopes.tfhe.ffm.U256::allocate, BIT_SIZE, false, MIN_VALUE, MAX_VALUE);
  }

  @NonNull
  public static U256 valueOf(@NonNull BigInteger value) {
    U256 u256 = new U256();
    u256.setValue(value);
    return u256;
  }

  @Override
  protected void setWord(int index, long word) {
    switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.U256.w0(getAddress(), word);
      case 1 -> io.github.rdlopes.tfhe.ffm.U256.w1(getAddress(), word);
      case 2 -> io.github.rdlopes.tfhe.ffm.U256.w2(getAddress(), word);
      case 3 -> io.github.rdlopes.tfhe.ffm.U256.w3(getAddress(), word);
      default -> throw new IndexOutOfBoundsException("Index must be 0 to 3");
    }
  }

  @Override
  protected long getWord(int index) {
    return switch (index) {
      case 0 -> io.github.rdlopes.tfhe.ffm.U256.w0(getAddress());
      case 1 -> io.github.rdlopes.tfhe.ffm.U256.w1(getAddress());
      case 2 -> io.github.rdlopes.tfhe.ffm.U256.w2(getAddress());
      case 3 -> io.github.rdlopes.tfhe.ffm.U256.w3(getAddress());
      default -> throw new IndexOutOfBoundsException("Index must be 0 to 3");
    };
  }
}
