package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.math.BigInteger;

public class U256 extends GroupLayoutPointer {

  protected U256() {
    super(
      io.github.rdlopes.tfhe.ffm.U256.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.U256.layout()
    );
  }

  public static U256 valueOf(long value) {
    if (value < 0) {
      throw new IllegalArgumentException("U256 cannot represent negative values");
    }
    return valueOf(BigInteger.valueOf(value));
  }

  public static U256 valueOf(String value) {
    BigInteger bigInt = new BigInteger(value);
    if (bigInt.signum() < 0) {
      throw new IllegalArgumentException("U256 cannot represent negative values");
    }
    return valueOf(bigInt);
  }

  public static U256 valueOf(BigInteger value) {
    if (value.signum() < 0) {
      throw new IllegalArgumentException("U256 cannot represent negative values");
    }
    if (value.bitLength() > 256) {
      throw new IllegalArgumentException("Value exceeds U256 maximum capacity (256 bits)");
    }

    U256 u256 = new U256();

    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);
    long w0 = value.and(mask64)
                   .longValue();
    long w1 = value.shiftRight(64)
                   .and(mask64)
                   .longValue();
    long w2 = value.shiftRight(128)
                   .and(mask64)
                   .longValue();
    long w3 = value.shiftRight(192)
                   .and(mask64)
                   .longValue();

    io.github.rdlopes.tfhe.ffm.U256.w0(u256.getAddress(), w0);
    io.github.rdlopes.tfhe.ffm.U256.w1(u256.getAddress(), w1);
    io.github.rdlopes.tfhe.ffm.U256.w2(u256.getAddress(), w2);
    io.github.rdlopes.tfhe.ffm.U256.w3(u256.getAddress(), w3);

    return u256;
  }

  public static U256 valueOf(long w3, long w2, long w1, long w0) {
    U256 u256 = new U256();
    io.github.rdlopes.tfhe.ffm.U256.w0(u256.getAddress(), w0);
    io.github.rdlopes.tfhe.ffm.U256.w1(u256.getAddress(), w1);
    io.github.rdlopes.tfhe.ffm.U256.w2(u256.getAddress(), w2);
    io.github.rdlopes.tfhe.ffm.U256.w3(u256.getAddress(), w3);
    return u256;
  }

  public BigInteger getValue() {
    long w0 = io.github.rdlopes.tfhe.ffm.U256.w0(getAddress());
    long w1 = io.github.rdlopes.tfhe.ffm.U256.w1(getAddress());
    long w2 = io.github.rdlopes.tfhe.ffm.U256.w2(getAddress());
    long w3 = io.github.rdlopes.tfhe.ffm.U256.w3(getAddress());

    BigInteger part0 = BigInteger.valueOf(w0)
                                 .and(BigInteger.ONE.shiftLeft(64)
                                                    .subtract(BigInteger.ONE));
    BigInteger part1 = BigInteger.valueOf(w1)
                                 .and(BigInteger.ONE.shiftLeft(64)
                                                    .subtract(BigInteger.ONE));
    BigInteger part2 = BigInteger.valueOf(w2)
                                 .and(BigInteger.ONE.shiftLeft(64)
                                                    .subtract(BigInteger.ONE));
    BigInteger part3 = BigInteger.valueOf(w3)
                                 .and(BigInteger.ONE.shiftLeft(64)
                                                    .subtract(BigInteger.ONE));

    return part3.shiftLeft(192)
                .or(part2.shiftLeft(128))
                .or(part1.shiftLeft(64))
                .or(part0);
  }

  public long getW0() {
    return io.github.rdlopes.tfhe.ffm.U256.w0(getAddress());
  }

  public long getW1() {
    return io.github.rdlopes.tfhe.ffm.U256.w1(getAddress());
  }

  public long getW2() {
    return io.github.rdlopes.tfhe.ffm.U256.w2(getAddress());
  }

  public long getW3() {
    return io.github.rdlopes.tfhe.ffm.U256.w3(getAddress());
  }

  @Override
  public String toString() {
    return getValue().toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    U256 other = (U256) obj;
    return getValue().equals(other.getValue());
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }
}
