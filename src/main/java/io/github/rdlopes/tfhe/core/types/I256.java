package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.math.BigInteger;

public class I256 extends GroupLayoutPointer {

  protected I256() {
    super(
      io.github.rdlopes.tfhe.ffm.I256.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.I256.layout()
    );
  }

  public static I256 valueOf(long value) {
    return valueOf(BigInteger.valueOf(value));
  }

  public static I256 valueOf(String value) {
    return valueOf(new BigInteger(value));
  }

  public static I256 valueOf(BigInteger value) {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(255)
                                    .subtract(BigInteger.ONE);
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(255)
                                    .negate();

    if (value.compareTo(maxValue) > 0) {
      throw new IllegalArgumentException("Value exceeds I256 maximum capacity (2^255 - 1)");
    }
    if (value.compareTo(minValue) < 0) {
      throw new IllegalArgumentException("Value exceeds I256 minimum capacity (-2^255)");
    }

    I256 i256 = new I256();

    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    if (value.signum() >= 0) {
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

      io.github.rdlopes.tfhe.ffm.I256.w0(i256.getAddress(), w0);
      io.github.rdlopes.tfhe.ffm.I256.w1(i256.getAddress(), w1);
      io.github.rdlopes.tfhe.ffm.I256.w2(i256.getAddress(), w2);
      io.github.rdlopes.tfhe.ffm.I256.w3(i256.getAddress(), w3);
    } else {
      BigInteger positiveValue = value.add(BigInteger.ONE.shiftLeft(256));
      long w0 = positiveValue.and(mask64)
                             .longValue();
      long w1 = positiveValue.shiftRight(64)
                             .and(mask64)
                             .longValue();
      long w2 = positiveValue.shiftRight(128)
                             .and(mask64)
                             .longValue();
      long w3 = positiveValue.shiftRight(192)
                             .and(mask64)
                             .longValue();

      io.github.rdlopes.tfhe.ffm.I256.w0(i256.getAddress(), w0);
      io.github.rdlopes.tfhe.ffm.I256.w1(i256.getAddress(), w1);
      io.github.rdlopes.tfhe.ffm.I256.w2(i256.getAddress(), w2);
      io.github.rdlopes.tfhe.ffm.I256.w3(i256.getAddress(), w3);
    }

    return i256;
  }

  public static I256 valueOf(long w3, long w2, long w1, long w0) {
    I256 i256 = new I256();
    io.github.rdlopes.tfhe.ffm.I256.w0(i256.getAddress(), w0);
    io.github.rdlopes.tfhe.ffm.I256.w1(i256.getAddress(), w1);
    io.github.rdlopes.tfhe.ffm.I256.w2(i256.getAddress(), w2);
    io.github.rdlopes.tfhe.ffm.I256.w3(i256.getAddress(), w3);
    return i256;
  }

  public BigInteger getValue() {
    long w0 = io.github.rdlopes.tfhe.ffm.I256.w0(getAddress());
    long w1 = io.github.rdlopes.tfhe.ffm.I256.w1(getAddress());
    long w2 = io.github.rdlopes.tfhe.ffm.I256.w2(getAddress());
    long w3 = io.github.rdlopes.tfhe.ffm.I256.w3(getAddress());

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

    BigInteger result = part3.shiftLeft(192)
                             .or(part2.shiftLeft(128))
                             .or(part1.shiftLeft(64))
                             .or(part0);

    if (result.testBit(255)) {
      result = result.subtract(BigInteger.ONE.shiftLeft(256));
    }

    return result;
  }

  public long getW0() {
    return io.github.rdlopes.tfhe.ffm.I256.w0(getAddress());
  }

  public long getW1() {
    return io.github.rdlopes.tfhe.ffm.I256.w1(getAddress());
  }

  public long getW2() {
    return io.github.rdlopes.tfhe.ffm.I256.w2(getAddress());
  }

  public long getW3() {
    return io.github.rdlopes.tfhe.ffm.I256.w3(getAddress());
  }

  @Override
  public String toString() {
    return getValue().toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    I256 other = (I256) obj;
    return getValue().equals(other.getValue());
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }
}
