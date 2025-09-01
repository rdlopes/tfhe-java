package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.I512;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class I512Test {

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    I512 i512 = I512.valueOf(value);

    assertThat(i512).isNotNull();
    assertThat(i512.getAddress()).isNotNull();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    I512 i512 = I512.valueOf(originalValue);

    assertThat(i512).isEqualTo(originalValue);
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    I512 i512 = I512.valueOf(zero);

    assertThat(i512).isEqualTo(zero);
  }

  @Test
  void handlesNegativeValue() {
    long negativeValue = -123456789L;
    I512 i512 = I512.valueOf(negativeValue);

    assertThat(i512).isEqualTo(negativeValue);
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789012345678901234567890";
    I512 i512 = I512.valueOf(largeValue);

    assertThat(i512).isEqualTo(largeValue);
  }

  @Test
  void handlesLargeNegativeValue() {
    String largeNegativeValue = "-123456789012345678901234567890123456789012345678901234567890";
    I512 i512 = I512.valueOf(largeNegativeValue);

    assertThat(i512).isEqualTo(largeNegativeValue);
  }

  @Test
  void handlesMaximumValueRange() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(511)
                                    .subtract(BigInteger.ONE);
    I512 i512 = I512.valueOf(maxValue);

    assertThat(i512).isEqualTo(maxValue);
  }

  @Test
  void handlesMinimumValueRange() {
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(511)
                                    .negate();
    I512 i512 = I512.valueOf(minValue);

    assertThat(i512).isEqualTo(minValue);
  }

  @Test
  void rejectsValueExceedingMaximumCapacity() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(511);

    assertThatThrownBy(() -> I512.valueOf(tooLargeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I512 maximum capacity (2^511 - 1)");
  }

  @Test
  void rejectsValueExceedingMinimumCapacity() {
    BigInteger tooSmallValue = BigInteger.valueOf(2)
                                         .pow(511)
                                         .negate()
                                         .subtract(BigInteger.ONE);

    assertThatThrownBy(() -> I512.valueOf(tooSmallValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I512 minimum capacity (-2^511)");
  }

  @Test
  void rejectsStringValueExceedingMaximumCapacity() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(511);

    assertThatThrownBy(() -> I512.valueOf(tooLargeValue.toString()))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I512 maximum capacity (2^511 - 1)");
  }

  @Test
  void rejectsStringValueExceedingMinimumCapacity() {
    BigInteger tooSmallValue = BigInteger.valueOf(2)
                                         .pow(511)
                                         .negate()
                                         .subtract(BigInteger.ONE);

    assertThatThrownBy(() -> I512.valueOf(tooSmallValue.toString()))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I512 minimum capacity (-2^511)");
  }

  @Test
  void createsFromWordArray() {
    long[] words = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L};

    I512 i512 = I512.valueOf(words);

    assertThat(i512).hasWords(words);
  }

  @Test
  void retrievesWordArrayCorrectly() {
    long[] originalWords = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L};

    I512 i512 = I512.valueOf(originalWords);
    long[] retrievedWords = i512.getWords();

    for (int i = 0; i < 8; i++) {
      assertThat(retrievedWords[i]).isEqualTo(originalWords[i]);
    }
  }

  @Test
  void retrievesIndividualWordCorrectly() {
    long[] words = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L};

    I512 i512 = I512.valueOf(words);

    for (int i = 0; i < 8; i++) {
      assertThat(i512.getWord(i)).isEqualTo(words[i]);
    }
  }

  @Test
  void rejectsInvalidWordArrayLength() {
    long[] invalidWords = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L};

    assertThatThrownBy(() -> I512.valueOf(invalidWords))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("I512 requires exactly 8 words");
  }

  @Test
  void rejectsInvalidWordIndex() {
    I512 i512 = I512.valueOf(12345L);

    assertThatThrownBy(() -> i512.getWord(-1))
      .isInstanceOf(IndexOutOfBoundsException.class)
      .hasMessage("Word index must be between 0 and 7");

    assertThatThrownBy(() -> i512.getWord(8))
      .isInstanceOf(IndexOutOfBoundsException.class)
      .hasMessage("Word index must be between 0 and 7");
  }

  @Test
  void handlesStringConversionCorrectly() {
    BigInteger value = BigInteger.valueOf(2)
                                 .pow(511)
                                 .negate();
    I512 i512 = I512.valueOf(value);

    assertThat(i512.toString()).isEqualTo(value.toString());
  }

  @Test
  void handlesEqualityCorrectly() {
    long value = -123456789L;
    I512 i512a = I512.valueOf(value);
    I512 i512b = I512.valueOf(value);

    assertThat(i512a.equals(i512b)).isTrue();
    assertThat(i512a.hashCode()).isEqualTo(i512b.hashCode());
  }

  @Test
  void handlesUnequalValues() {
    I512 i512a = I512.valueOf(123456789L);
    I512 i512b = I512.valueOf(-987654321L);

    assertThat(i512a.equals(i512b)).isFalse();
  }

  @Test
  void handlesNullInEquals() {
    I512 i512 = I512.valueOf(123456789L);

    assertThat(i512.equals(null)).isFalse();
  }

  @Test
  void handlesDifferentClassInEquals() {
    I512 i512 = I512.valueOf(123456789L);
    String other = "not an I512";

    assertThat(i512.equals(other)).isFalse();
  }

  @Test
  void handlesSelfEqualityInEquals() {
    I512 i512 = I512.valueOf(-123456789L);

    assertThat(i512.equals(i512)).isTrue();
  }
}
