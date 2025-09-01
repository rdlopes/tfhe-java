package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.math.BigInteger;

public class I1024 extends GroupLayoutPointer {

  protected I1024() {
    super(
      io.github.rdlopes.tfhe.ffm.I1024.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.I1024.layout()
    );
  }

  public static I1024 valueOf(long value) {
    return valueOf(BigInteger.valueOf(value));
  }

  public static I1024 valueOf(String value) {
    return valueOf(new BigInteger(value));
  }

  public static I1024 valueOf(BigInteger value) {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(1023)
                                    .subtract(BigInteger.ONE);
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(1023)
                                    .negate();

    if (value.compareTo(maxValue) > 0) {
      throw new IllegalArgumentException("Value exceeds I1024 maximum capacity (2^1023 - 1)");
    }
    if (value.compareTo(minValue) < 0) {
      throw new IllegalArgumentException("Value exceeds I1024 minimum capacity (-2^1023)");
    }

    I1024 i1024 = new I1024();

    BigInteger workValue = value.signum() < 0 ?
      value.add(BigInteger.ONE.shiftLeft(1024)) : value;

    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    for (int i = 0; i < 16; i++) {
      long word = workValue.shiftRight(i * 64)
                           .and(mask64)
                           .longValue();
      io.github.rdlopes.tfhe.ffm.I1024.words(i1024.getAddress(), i, word);
    }

    return i1024;
  }

  public static I1024 valueOf(long[] words) {
    if (words.length != 16) {
      throw new IllegalArgumentException("I1024 requires exactly 16 words");
    }

    I1024 i1024 = new I1024();
    for (int i = 0; i < 16; i++) {
      io.github.rdlopes.tfhe.ffm.I1024.words(i1024.getAddress(), i, words[i]);
    }
    return i1024;
  }

  public BigInteger getValue() {
    BigInteger result = BigInteger.ZERO;
    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    for (int i = 0; i < 16; i++) {
      long word = io.github.rdlopes.tfhe.ffm.I1024.words(getAddress(), i);
      BigInteger wordValue = BigInteger.valueOf(word)
                                       .and(mask64);
      result = result.or(wordValue.shiftLeft(i * 64));
    }

    if (result.testBit(1023)) {
      result = result.subtract(BigInteger.ONE.shiftLeft(1024));
    }

    return result;
  }

  public long getWord(int index) {
    if (index < 0 || index >= 16) {
      throw new IndexOutOfBoundsException("Word index must be between 0 and 15");
    }
    return io.github.rdlopes.tfhe.ffm.I1024.words(getAddress(), index);
  }

  public long[] getWords() {
    long[] words = new long[16];
    for (int i = 0; i < 16; i++) {
      words[i] = io.github.rdlopes.tfhe.ffm.I1024.words(getAddress(), i);
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
    I1024 other = (I1024) obj;
    return getValue().equals(other.getValue());
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }
}
