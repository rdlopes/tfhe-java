package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.U512;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class U512Test {

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    U512 u512 = U512.valueOf(value);

    assertThat(u512).isNotNull();
    assertThat(u512.getAddress()).isNotNull();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    U512 u512 = U512.valueOf(originalValue);

    assertThat(u512).isEqualTo(originalValue);
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    U512 u512 = U512.valueOf(zero);

    assertThat(u512).isEqualTo(zero);
  }

  @Test
  void rejectsNegativeValueFromLong() {
    long negativeValue = -123456789L;

    assertThatThrownBy(() -> U512.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U512 cannot represent negative values");
  }

  @Test
  void rejectsNegativeValueFromString() {
    String negativeValue = "-123456789";

    assertThatThrownBy(() -> U512.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U512 cannot represent negative values");
  }

  @Test
  void rejectsNegativeValueFromBigInteger() {
    BigInteger negativeValue = BigInteger.valueOf(-123456789L);

    assertThatThrownBy(() -> U512.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U512 cannot represent negative values");
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789012345678901234567890";
    U512 u512 = U512.valueOf(largeValue);

    assertThat(u512).isEqualTo(largeValue);
  }

  @Test
  void handlesMaximumU512Value() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(512)
                                    .subtract(BigInteger.ONE);
    U512 u512 = U512.valueOf(maxValue);

    assertThat(u512).isEqualTo(maxValue);
  }

  @Test
  void rejectsValueExceeding512Bits() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(513);

    assertThatThrownBy(() -> U512.valueOf(tooLargeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds U512 maximum capacity (512 bits)");
  }

  @Test
  void createsFromWordArray() {
    long[] words = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L};

    U512 u512 = U512.valueOf(words);

    assertThat(u512).hasWords(words);
  }

  @Test
  void retrievesWordArrayCorrectly() {
    long[] originalWords = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L};

    U512 u512 = U512.valueOf(originalWords);
    long[] retrievedWords = u512.getWords();

    for (int i = 0; i < 8; i++) {
      assertThat(retrievedWords[i]).isEqualTo(originalWords[i]);
    }
  }

  @Test
  void retrievesIndividualWordCorrectly() {
    long[] words = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L};

    U512 u512 = U512.valueOf(words);

    for (int i = 0; i < 8; i++) {
      assertThat(u512.getWord(i)).isEqualTo(words[i]);
    }
  }

  @Test
  void rejectsInvalidWordArrayLength() {
    long[] invalidWords = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L};

    assertThatThrownBy(() -> U512.valueOf(invalidWords))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U512 requires exactly 8 words");
  }

  @Test
  void rejectsInvalidWordIndex() {
    U512 u512 = U512.valueOf(12345L);

    assertThatThrownBy(() -> u512.getWord(-1))
      .isInstanceOf(IndexOutOfBoundsException.class)
      .hasMessage("Word index must be between 0 and 7");

    assertThatThrownBy(() -> u512.getWord(8))
      .isInstanceOf(IndexOutOfBoundsException.class)
      .hasMessage("Word index must be between 0 and 7");
  }

  @Test
  void handlesLargeValueWithWordArray() {
    BigInteger value = BigInteger.valueOf(Long.MAX_VALUE)
                                 .shiftLeft(448)
                                 .add(BigInteger.valueOf(Long.MAX_VALUE)
                                                .shiftLeft(384))
                                 .add(BigInteger.valueOf(Long.MAX_VALUE)
                                                .shiftLeft(320))
                                 .add(BigInteger.valueOf(Long.MAX_VALUE)
                                                .shiftLeft(256))
                                 .add(BigInteger.valueOf(Long.MAX_VALUE)
                                                .shiftLeft(192))
                                 .add(BigInteger.valueOf(Long.MAX_VALUE)
                                                .shiftLeft(128))
                                 .add(BigInteger.valueOf(Long.MAX_VALUE)
                                                .shiftLeft(64))
                                 .add(BigInteger.valueOf(Long.MAX_VALUE));

    U512 u512 = U512.valueOf(value);

    for (int i = 0; i < 8; i++) {
      assertThat(u512.getWord(i)).isEqualTo(Long.MAX_VALUE);
    }
    assertThat(u512).isEqualTo(value);
  }

  @Test
  void handlesStringConversionCorrectly() {
    BigInteger value = new BigInteger("13407807929942597099574024998205846127479365820592393377723561443721764030073546976801874298166903427690031858186486050853753882811946569946433649006084095");
    U512 u512 = U512.valueOf(value);

    assertThat(u512.toString()).isEqualTo(value.toString());
  }

  @Test
  void handlesEqualityCorrectly() {
    long value = 123456789L;
    U512 u512a = U512.valueOf(value);
    U512 u512b = U512.valueOf(value);

    assertThat(u512a.equals(u512b)).isTrue();
    assertThat(u512a.hashCode()).isEqualTo(u512b.hashCode());
  }

  @Test
  void handlesUnequalValues() {
    U512 u512a = U512.valueOf(123456789L);
    U512 u512b = U512.valueOf(987654321L);

    assertThat(u512a.equals(u512b)).isFalse();
  }

  @Test
  void handlesNullInEquals() {
    U512 u512 = U512.valueOf(123456789L);

    assertThat(u512.equals(null)).isFalse();
  }

  @Test
  void handlesDifferentClassInEquals() {
    U512 u512 = U512.valueOf(123456789L);
    String other = "not a U512";

    assertThat(u512.equals(other)).isFalse();
  }

  @Test
  void handlesSelfEqualityInEquals() {
    U512 u512 = U512.valueOf(123456789L);

    assertThat(u512.equals(u512)).isTrue();
  }
}
