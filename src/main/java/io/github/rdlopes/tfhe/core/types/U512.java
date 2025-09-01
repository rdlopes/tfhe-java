package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.math.BigInteger;

public class U512 extends GroupLayoutPointer {

  protected U512() {
    super(
      io.github.rdlopes.tfhe.ffm.U512.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.U512.layout()
    );
  }

  public static U512 valueOf(long value) {
    if (value < 0) {
      throw new IllegalArgumentException("U512 cannot represent negative values");
    }
    return valueOf(BigInteger.valueOf(value));
  }

  public static U512 valueOf(String value) {
    BigInteger bigInt = new BigInteger(value);
    if (bigInt.signum() < 0) {
      throw new IllegalArgumentException("U512 cannot represent negative values");
    }
    return valueOf(bigInt);
  }

  public static U512 valueOf(BigInteger value) {
    if (value.signum() < 0) {
      throw new IllegalArgumentException("U512 cannot represent negative values");
    }
    if (value.bitLength() > 512) {
      throw new IllegalArgumentException("Value exceeds U512 maximum capacity (512 bits)");
    }

    U512 u512 = new U512();

    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    for (int i = 0; i < 8; i++) {
      long word = value.shiftRight(i * 64)
                       .and(mask64)
                       .longValue();
      io.github.rdlopes.tfhe.ffm.U512.words(u512.getAddress(), i, word);
    }

    return u512;
  }

  public static U512 valueOf(long[] words) {
    if (words.length != 8) {
      throw new IllegalArgumentException("U512 requires exactly 8 words");
    }

    U512 u512 = new U512();
    for (int i = 0; i < 8; i++) {
      io.github.rdlopes.tfhe.ffm.U512.words(u512.getAddress(), i, words[i]);
    }
    return u512;
  }

  public BigInteger getValue() {
    BigInteger result = BigInteger.ZERO;
    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    for (int i = 0; i < 8; i++) {
      long word = io.github.rdlopes.tfhe.ffm.U512.words(getAddress(), i);
      BigInteger wordValue = BigInteger.valueOf(word)
                                       .and(mask64);
      result = result.or(wordValue.shiftLeft(i * 64));
    }

    return result;
  }

  public long getWord(int index) {
    if (index < 0 || index >= 8) {
      throw new IndexOutOfBoundsException("Word index must be between 0 and 7");
    }
    return io.github.rdlopes.tfhe.ffm.U512.words(getAddress(), index);
  }

  public long[] getWords() {
    long[] words = new long[8];
    for (int i = 0; i < 8; i++) {
      words[i] = io.github.rdlopes.tfhe.ffm.U512.words(getAddress(), i);
    }
    return words;
  }

  @Override
  public String toString() {
    return getValue().toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    U512 other = (U512) obj;
    return getValue().equals(other.getValue());
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }
}
