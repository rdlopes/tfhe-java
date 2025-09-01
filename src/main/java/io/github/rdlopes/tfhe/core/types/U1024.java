package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.math.BigInteger;

public class U1024 extends GroupLayoutPointer {

  protected U1024() {
    super(
      io.github.rdlopes.tfhe.ffm.U1024.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.U1024.layout()
    );
  }

  public static U1024 valueOf(long value) {
    if (value < 0) {
      throw new IllegalArgumentException("U1024 cannot represent negative values");
    }
    return valueOf(BigInteger.valueOf(value));
  }

  public static U1024 valueOf(String value) {
    BigInteger bigInt = new BigInteger(value);
    if (bigInt.signum() < 0) {
      throw new IllegalArgumentException("U1024 cannot represent negative values");
    }
    return valueOf(bigInt);
  }

  public static U1024 valueOf(BigInteger value) {
    if (value.signum() < 0) {
      throw new IllegalArgumentException("U1024 cannot represent negative values");
    }
    if (value.bitLength() > 1024) {
      throw new IllegalArgumentException("Value exceeds U1024 maximum capacity (1024 bits)");
    }

    U1024 u1024 = new U1024();

    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    for (int i = 0; i < 16; i++) {
      long word = value.shiftRight(i * 64)
                       .and(mask64)
                       .longValue();
      io.github.rdlopes.tfhe.ffm.U1024.words(u1024.getAddress(), i, word);
    }

    return u1024;
  }

  public static U1024 valueOf(long[] words) {
    if (words.length != 16) {
      throw new IllegalArgumentException("U1024 requires exactly 16 words");
    }

    U1024 u1024 = new U1024();
    for (int i = 0; i < 16; i++) {
      io.github.rdlopes.tfhe.ffm.U1024.words(u1024.getAddress(), i, words[i]);
    }
    return u1024;
  }

  public BigInteger getValue() {
    BigInteger result = BigInteger.ZERO;
    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    for (int i = 0; i < 16; i++) {
      long word = io.github.rdlopes.tfhe.ffm.U1024.words(getAddress(), i);
      BigInteger wordValue = BigInteger.valueOf(word)
                                       .and(mask64);
      result = result.or(wordValue.shiftLeft(i * 64));
    }

    return result;
  }

  public long getWord(int index) {
    if (index < 0 || index >= 16) {
      throw new IndexOutOfBoundsException("Word index must be between 0 and 15");
    }
    return io.github.rdlopes.tfhe.ffm.U1024.words(getAddress(), index);
  }

  public long[] getWords() {
    long[] words = new long[16];
    for (int i = 0; i < 16; i++) {
      words[i] = io.github.rdlopes.tfhe.ffm.U1024.words(getAddress(), i);
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
    U1024 other = (U1024) obj;
    return getValue().equals(other.getValue());
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }
}
