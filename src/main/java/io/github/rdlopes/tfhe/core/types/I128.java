package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.math.BigInteger;

public class I128 extends GroupLayoutPointer {

  protected I128() {
    super(
      io.github.rdlopes.tfhe.ffm.I128.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.I128.layout()
    );
  }

  public static I128 valueOf(long value) {
    return valueOf(BigInteger.valueOf(value));
  }

  public static I128 valueOf(String value) {
    return valueOf(new BigInteger(value));
  }

  public static I128 valueOf(BigInteger value) {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(127)
                                    .subtract(BigInteger.ONE);
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(127)
                                    .negate();

    if (value.compareTo(maxValue) > 0) {
      throw new IllegalArgumentException("Value exceeds I128 maximum capacity (2^127 - 1)");
    }
    if (value.compareTo(minValue) < 0) {
      throw new IllegalArgumentException("Value exceeds I128 minimum capacity (-2^127)");
    }

    I128 i128 = new I128();

    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    if (value.signum() >= 0) {
      long w0 = value.and(mask64)
                     .longValue();
      long w1 = value.shiftRight(64)
                     .and(mask64)
                     .longValue();

      io.github.rdlopes.tfhe.ffm.I128.w0(i128.getAddress(), w0);
      io.github.rdlopes.tfhe.ffm.I128.w1(i128.getAddress(), w1);
    } else {
      BigInteger positiveValue = value.add(BigInteger.ONE.shiftLeft(128));
      long w0 = positiveValue.and(mask64)
                             .longValue();
      long w1 = positiveValue.shiftRight(64)
                             .and(mask64)
                             .longValue();

      io.github.rdlopes.tfhe.ffm.I128.w0(i128.getAddress(), w0);
      io.github.rdlopes.tfhe.ffm.I128.w1(i128.getAddress(), w1);
    }

    return i128;
  }

  public static I128 valueOf(long w1, long w0) {
    I128 i128 = new I128();
    io.github.rdlopes.tfhe.ffm.I128.w0(i128.getAddress(), w0);
    io.github.rdlopes.tfhe.ffm.I128.w1(i128.getAddress(), w1);
    return i128;
  }

  public BigInteger getValue() {
    long w0 = io.github.rdlopes.tfhe.ffm.I128.w0(getAddress());
    long w1 = io.github.rdlopes.tfhe.ffm.I128.w1(getAddress());

    BigInteger lowPart = BigInteger.valueOf(w0)
                                   .and(BigInteger.ONE.shiftLeft(64)
                                                      .subtract(BigInteger.ONE));
    BigInteger highPart = BigInteger.valueOf(w1)
                                    .and(BigInteger.ONE.shiftLeft(64)
                                                       .subtract(BigInteger.ONE));

    BigInteger result = highPart.shiftLeft(64)
                                .or(lowPart);

    if (result.testBit(127)) {
      result = result.subtract(BigInteger.ONE.shiftLeft(128));
    }

    return result;
  }

  public long getW0() {
    return io.github.rdlopes.tfhe.ffm.I128.w0(getAddress());
  }

  public long getW1() {
    return io.github.rdlopes.tfhe.ffm.I128.w1(getAddress());
  }

  @Override
  public String toString() {
    return getValue().toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    I128 other = (I128) obj;
    return getValue().equals(other.getValue());
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }
}
