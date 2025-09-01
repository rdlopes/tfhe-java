package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.math.BigInteger;

public class U128 extends GroupLayoutPointer {

  protected U128() {
    super(
      io.github.rdlopes.tfhe.ffm.U128.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.U128.layout()
    );
  }

  public static U128 valueOf(long value) {
    if (value < 0) {
      throw new IllegalArgumentException("U128 cannot represent negative values");
    }
    return valueOf(BigInteger.valueOf(value));
  }

  public static U128 valueOf(String value) {
    BigInteger bigInt = new BigInteger(value);
    if (bigInt.signum() < 0) {
      throw new IllegalArgumentException("U128 cannot represent negative values");
    }
    return valueOf(bigInt);
  }

  public static U128 valueOf(BigInteger value) {
    if (value.signum() < 0) {
      throw new IllegalArgumentException("U128 cannot represent negative values");
    }
    if (value.bitLength() > 128) {
      throw new IllegalArgumentException("Value exceeds U128 maximum capacity (128 bits)");
    }

    U128 u128 = new U128();

    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);
    long w0 = value.and(mask64)
                   .longValue();
    long w1 = value.shiftRight(64)
                   .and(mask64)
                   .longValue();

    io.github.rdlopes.tfhe.ffm.U128.w0(u128.getAddress(), w0);
    io.github.rdlopes.tfhe.ffm.U128.w1(u128.getAddress(), w1);

    return u128;
  }

  public static U128 valueOf(long w1, long w0) {
    U128 u128 = new U128();
    io.github.rdlopes.tfhe.ffm.U128.w0(u128.getAddress(), w0);
    io.github.rdlopes.tfhe.ffm.U128.w1(u128.getAddress(), w1);
    return u128;
  }

  public BigInteger getValue() {
    long w0 = io.github.rdlopes.tfhe.ffm.U128.w0(getAddress());
    long w1 = io.github.rdlopes.tfhe.ffm.U128.w1(getAddress());

    BigInteger lowPart = BigInteger.valueOf(w0)
                                   .and(BigInteger.ONE.shiftLeft(64)
                                                      .subtract(BigInteger.ONE));
    BigInteger highPart = BigInteger.valueOf(w1)
                                    .and(BigInteger.ONE.shiftLeft(64)
                                                       .subtract(BigInteger.ONE));

    return highPart.shiftLeft(64)
                   .or(lowPart);
  }

  public long getW0() {
    return io.github.rdlopes.tfhe.ffm.U128.w0(getAddress());
  }

  public long getW1() {
    return io.github.rdlopes.tfhe.ffm.U128.w1(getAddress());
  }

  @Override
  public String toString() {
    return getValue().toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    U128 other = (U128) obj;
    return getValue().equals(other.getValue());
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }
}
