package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.ffm.GroupLayoutPointer;

import java.math.BigInteger;

public class I512 extends GroupLayoutPointer {

  protected I512() {
    super(
      io.github.rdlopes.tfhe.ffm.I512.allocate(ARENA),
      io.github.rdlopes.tfhe.ffm.I512.layout()
    );
  }

  public static I512 valueOf(long value) {
    return valueOf(BigInteger.valueOf(value));
  }

  public static I512 valueOf(String value) {
    return valueOf(new BigInteger(value));
  }

  public static I512 valueOf(BigInteger value) {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(511)
                                    .subtract(BigInteger.ONE);
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(511)
                                    .negate();

    if (value.compareTo(maxValue) > 0) {
      throw new IllegalArgumentException("Value exceeds I512 maximum capacity (2^511 - 1)");
    }
    if (value.compareTo(minValue) < 0) {
      throw new IllegalArgumentException("Value exceeds I512 minimum capacity (-2^511)");
    }

    I512 i512 = new I512();

    BigInteger workValue = value.signum() < 0 ?
      value.add(BigInteger.ONE.shiftLeft(512)) : value;

    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    for (int i = 0; i < 8; i++) {
      long word = workValue.shiftRight(i * 64)
                           .and(mask64)
                           .longValue();
      io.github.rdlopes.tfhe.ffm.I512.words(i512.getAddress(), i, word);
    }

    return i512;
  }

  public static I512 valueOf(long[] words) {
    if (words.length != 8) {
      throw new IllegalArgumentException("I512 requires exactly 8 words");
    }

    I512 i512 = new I512();
    for (int i = 0; i < 8; i++) {
      io.github.rdlopes.tfhe.ffm.I512.words(i512.getAddress(), i, words[i]);
    }
    return i512;
  }

  public BigInteger getValue() {
    BigInteger result = BigInteger.ZERO;
    BigInteger mask64 = BigInteger.ONE.shiftLeft(64)
                                      .subtract(BigInteger.ONE);

    for (int i = 0; i < 8; i++) {
      long word = io.github.rdlopes.tfhe.ffm.I512.words(getAddress(), i);
      BigInteger wordValue = BigInteger.valueOf(word)
                                       .and(mask64);
      result = result.or(wordValue.shiftLeft(i * 64));
    }

    if (result.testBit(511)) {
      result = result.subtract(BigInteger.ONE.shiftLeft(512));
    }

    return result;
  }

  public long getWord(int index) {
    if (index < 0 || index >= 8) {
      throw new IndexOutOfBoundsException("Word index must be between 0 and 7");
    }
    return io.github.rdlopes.tfhe.ffm.I512.words(getAddress(), index);
  }

  public long[] getWords() {
    long[] words = new long[8];
    for (int i = 0; i < 8; i++) {
      words[i] = io.github.rdlopes.tfhe.ffm.I512.words(getAddress(), i);
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
    I512 other = (I512) obj;
    return getValue().equals(other.getValue());
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }
}
