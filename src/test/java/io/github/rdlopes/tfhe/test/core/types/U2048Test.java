package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.U2048;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class U2048Test {

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    U2048 u2048 = U2048.valueOf(value);

    assertThat(u2048).isNotNull();
    assertThat(u2048.getAddress()).isNotNull();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    U2048 u2048 = U2048.valueOf(originalValue);

    assertThat(u2048).isEqualTo(originalValue);
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    U2048 u2048 = U2048.valueOf(zero);

    assertThat(u2048).isEqualTo(zero);
  }

  @Test
  void rejectsNegativeValueFromLong() {
    long negativeValue = -123456789L;

    assertThatThrownBy(() -> U2048.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U2048 cannot represent negative values");
  }

  @Test
  void rejectsNegativeValueFromString() {
    String negativeValue = "-123456789";

    assertThatThrownBy(() -> U2048.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U2048 cannot represent negative values");
  }

  @Test
  void rejectsNegativeValueFromBigInteger() {
    BigInteger negativeValue = BigInteger.valueOf(-123456789L);

    assertThatThrownBy(() -> U2048.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U2048 cannot represent negative values");
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789012345678901234567890";
    U2048 u2048 = U2048.valueOf(largeValue);

    assertThat(u2048).isEqualTo(largeValue);
  }

  @Test
  void handlesMaximumU2048Value() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(2048)
                                    .subtract(BigInteger.ONE);
    U2048 u2048 = U2048.valueOf(maxValue);

    assertThat(u2048).isEqualTo(maxValue);
  }

  @Test
  void rejectsValueExceeding2048Bits() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(2049);

    assertThatThrownBy(() -> U2048.valueOf(tooLargeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds U2048 maximum capacity (2048 bits)");
  }

  @Test
  void createsFromWordArray() {
    long[] words = new long[32];
    for (int i = 0; i < 32; i++) {
      words[i] = 0x1234567890ABCDEFL + i;
    }

    U2048 u2048 = U2048.valueOf(words);

    assertThat(u2048).hasWords(words);
  }

  @Test
  void retrievesWordArrayCorrectly() {
    long[] originalWords = new long[32];
    for (int i = 0; i < 32; i++) {
      originalWords[i] = 0xFEDCBA0987654321L + i;
    }

    U2048 u2048 = U2048.valueOf(originalWords);
    long[] retrievedWords = u2048.getWords();

    for (int i = 0; i < 32; i++) {
      assertThat(retrievedWords[i]).isEqualTo(originalWords[i]);
    }
  }

  @Test
  void retrievesIndividualWordCorrectly() {
    long[] words = new long[32];
    for (int i = 0; i < 32; i++) {
      words[i] = 0x1111222233334444L + (i * 0x1111111111111111L);
    }

    U2048 u2048 = U2048.valueOf(words);

    for (int i = 0; i < 32; i++) {
      assertThat(u2048.getWord(i)).isEqualTo(words[i]);
    }
  }

  @Test
  void rejectsInvalidWordArrayLength() {
    long[] invalidWords = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L};

    assertThatThrownBy(() -> U2048.valueOf(invalidWords))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U2048 requires exactly 32 words");
  }

  @Test
  void rejectsInvalidWordIndex() {
    U2048 u2048 = U2048.valueOf(12345L);

    assertThatThrownBy(() -> u2048.getWord(-1))
      .isInstanceOf(IndexOutOfBoundsException.class)
      .hasMessage("Word index must be between 0 and 31");

    assertThatThrownBy(() -> u2048.getWord(32))
      .isInstanceOf(IndexOutOfBoundsException.class)
      .hasMessage("Word index must be between 0 and 31");
  }

  @Test
  void handlesLargeValueWithWordArray() {
    BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
    for (int i = 1; i < 32; i++) {
      value = value.add(BigInteger.valueOf(Long.MAX_VALUE)
                                  .shiftLeft(i * 64));
    }

    U2048 u2048 = U2048.valueOf(value);

    for (int i = 0; i < 32; i++) {
      assertThat(u2048.getWord(i)).isEqualTo(Long.MAX_VALUE);
    }
    assertThat(u2048).isEqualTo(value);
  }

  @Test
  void handlesStringConversionCorrectly() {
    BigInteger value = BigInteger.valueOf(2)
                                 .pow(2047)
                                 .subtract(BigInteger.ONE);
    U2048 u2048 = U2048.valueOf(value);

    assertThat(u2048.toString()).isEqualTo(value.toString());
  }

  @Test
  void handlesEqualityCorrectly() {
    long value = 123456789L;
    U2048 u2048a = U2048.valueOf(value);
    U2048 u2048b = U2048.valueOf(value);

    assertThat(u2048a.equals(u2048b)).isTrue();
    assertThat(u2048a.hashCode()).isEqualTo(u2048b.hashCode());
  }

  @Test
  void handlesUnequalValues() {
    U2048 u2048a = U2048.valueOf(123456789L);
    U2048 u2048b = U2048.valueOf(987654321L);

    assertThat(u2048a.equals(u2048b)).isFalse();
  }

  @Test
  void handlesNullInEquals() {
    U2048 u2048 = U2048.valueOf(123456789L);

    assertThat(u2048.equals(null)).isFalse();
  }

  @Test
  void handlesDifferentClassInEquals() {
    U2048 u2048 = U2048.valueOf(123456789L);
    String other = "not a U2048";

    assertThat(u2048.equals(other)).isFalse();
  }

  @Test
  void handlesSelfEqualityInEquals() {
    U2048 u2048 = U2048.valueOf(123456789L);

    assertThat(u2048.equals(u2048)).isTrue();
  }
}
