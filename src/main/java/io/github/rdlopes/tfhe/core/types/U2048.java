package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.math.BigInteger;

public class U2048 extends GroupLayoutPointer {

  protected U2048() {
    super(
      io.github.rdlopes.tfhe.ffm.U2048.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.U2048.layout()
    );
  }

  public static U2048 valueOf(long value) {
    if (value < 0) {
      throw new IllegalArgumentException("U2048 cannot represent negative values");
    }
    return valueOf(BigInteger.valueOf(value));
  }

  public static U2048 valueOf(String value) {
    BigInteger bigInt = new BigInteger(value);
    if (bigInt.signum() < 0) {
      throw new IllegalArgumentException("U2048 cannot represent negative values");
    }
    return valueOf(bigInt);
  }

  public static U2048 valueOf(BigInteger value) {
    if (value.signum() < 0) {
      throw new IllegalArgumentException("U2048 cannot represent negative values");
    }
    if (value.bitLength() > 2048) {
      throw new IllegalArgumentException("Value exceeds U2048 maximum capacity (2048 bits)");
    }

    U2048 u2048 = new U2048();

    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    for (int i = 0; i < 32; i++) {
      long word = value.shiftRight(i * 64)
                       .and(mask64)
                       .longValue();
      io.github.rdlopes.tfhe.ffm.U2048.words(u2048.getAddress(), i, word);
    }

    return u2048;
  }

  public static U2048 valueOf(long[] words) {
    if (words.length != 32) {
      throw new IllegalArgumentException("U2048 requires exactly 32 words");
    }

    U2048 u2048 = new U2048();
    for (int i = 0; i < 32; i++) {
      io.github.rdlopes.tfhe.ffm.U2048.words(u2048.getAddress(), i, words[i]);
    }
    return u2048;
  }

  public BigInteger getValue() {
    BigInteger result = BigInteger.ZERO;
    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    for (int i = 0; i < 32; i++) {
      long word = io.github.rdlopes.tfhe.ffm.U2048.words(getAddress(), i);
      BigInteger wordValue = BigInteger.valueOf(word)
                                       .and(mask64);
      result = result.or(wordValue.shiftLeft(i * 64));
    }

    return result;
  }

  public long getWord(int index) {
    if (index < 0 || index >= 32) {
      throw new IndexOutOfBoundsException("Word index must be between 0 and 31");
    }
    return io.github.rdlopes.tfhe.ffm.U2048.words(getAddress(), index);
  }

  public long[] getWords() {
    long[] words = new long[32];
    for (int i = 0; i < 32; i++) {
      words[i] = io.github.rdlopes.tfhe.ffm.U2048.words(getAddress(), i);
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
    U2048 other = (U2048) obj;
    return getValue().equals(other.getValue());
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }
}
