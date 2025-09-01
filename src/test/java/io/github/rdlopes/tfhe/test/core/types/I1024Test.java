package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.I1024;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class I1024Test {

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    I1024 i1024 = I1024.valueOf(value);

    assertThat(i1024).isNotNull();
    assertThat(i1024.getAddress()).isNotNull();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    I1024 i1024 = I1024.valueOf(originalValue);

    assertThat(i1024).isEqualTo(originalValue);
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    I1024 i1024 = I1024.valueOf(zero);

    assertThat(i1024).isEqualTo(zero);
  }

  @Test
  void handlesNegativeValue() {
    long negativeValue = -123456789L;
    I1024 i1024 = I1024.valueOf(negativeValue);

    assertThat(i1024).isEqualTo(negativeValue);
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789012345678901234567890";
    I1024 i1024 = I1024.valueOf(largeValue);

    assertThat(i1024).isEqualTo(largeValue);
  }

  @Test
  void handlesLargeNegativeValue() {
    String largeNegativeValue = "-123456789012345678901234567890123456789012345678901234567890";
    I1024 i1024 = I1024.valueOf(largeNegativeValue);

    assertThat(i1024).isEqualTo(largeNegativeValue);
  }

  @Test
  void handlesMaximumValueRange() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(1023)
                                    .subtract(BigInteger.ONE);
    I1024 i1024 = I1024.valueOf(maxValue);

    assertThat(i1024).isEqualTo(maxValue);
  }

  @Test
  void handlesMinimumValueRange() {
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(1023)
                                    .negate();
    I1024 i1024 = I1024.valueOf(minValue);

    assertThat(i1024).isEqualTo(minValue);
  }

  @Test
  void rejectsValueExceedingMaximumCapacity() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(1023);

    assertThatThrownBy(() -> I1024.valueOf(tooLargeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I1024 maximum capacity (2^1023 - 1)");
  }

  @Test
  void rejectsValueExceedingMinimumCapacity() {
    BigInteger tooSmallValue = BigInteger.valueOf(2)
                                         .pow(1023)
                                         .negate()
                                         .subtract(BigInteger.ONE);

    assertThatThrownBy(() -> I1024.valueOf(tooSmallValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I1024 minimum capacity (-2^1023)");
  }

  @Test
  void rejectsStringValueExceedingMaximumCapacity() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(1023);

    assertThatThrownBy(() -> I1024.valueOf(tooLargeValue.toString()))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I1024 maximum capacity (2^1023 - 1)");
  }

  @Test
  void rejectsStringValueExceedingMinimumCapacity() {
    BigInteger tooSmallValue = BigInteger.valueOf(2)
                                         .pow(1023)
                                         .negate()
                                         .subtract(BigInteger.ONE);

    assertThatThrownBy(() -> I1024.valueOf(tooSmallValue.toString()))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I1024 minimum capacity (-2^1023)");
  }

  @Test
  void createsFromWordArray() {
    long[] words = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L,
      0x5555555566666666L, 0x7777777788888888L, 0x9999999900000000L, 0xAAAABBBBCCCCDDDDL,
      0xEEEEFFFF11112222L, 0x3333444455556666L, 0x7777888899990000L, 0xAAAABBBBCCCCDDDDL};

    I1024 i1024 = I1024.valueOf(words);

    assertThat(i1024).hasWords(words);
  }

  @Test
  void retrievesWordArrayCorrectly() {
    long[] originalWords = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L,
      0x5555555566666666L, 0x7777777788888888L, 0x9999999900000000L, 0xAAAABBBBCCCCDDDDL,
      0xEEEEFFFF11112222L, 0x3333444455556666L, 0x7777888899990000L, 0xAAAABBBBCCCCDDDDL};

    I1024 i1024 = I1024.valueOf(originalWords);
    long[] retrievedWords = i1024.getWords();

    for (int i = 0; i < 16; i++) {
      assertThat(retrievedWords[i]).isEqualTo(originalWords[i]);
    }
  }

  @Test
  void retrievesIndividualWordCorrectly() {
    long[] words = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L,
      0x5555555566666666L, 0x7777777788888888L, 0x9999999900000000L, 0xAAAABBBBCCCCDDDDL,
      0xEEEEFFFF11112222L, 0x3333444455556666L, 0x7777888899990000L, 0xAAAABBBBCCCCDDDDL};

    I1024 i1024 = I1024.valueOf(words);

    for (int i = 0; i < 16; i++) {
      assertThat(i1024.getWord(i)).isEqualTo(words[i]);
    }
  }

  @Test
  void rejectsInvalidWordArrayLength() {
    long[] invalidWords = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L};

    assertThatThrownBy(() -> I1024.valueOf(invalidWords))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("I1024 requires exactly 16 words");
  }

  @Test
  void rejectsInvalidWordIndex() {
    I1024 i1024 = I1024.valueOf(12345L);

    assertThatThrownBy(() -> i1024.getWord(-1))
      .isInstanceOf(IndexOutOfBoundsException.class)
      .hasMessage("Word index must be between 0 and 15");

    assertThatThrownBy(() -> i1024.getWord(16))
      .isInstanceOf(IndexOutOfBoundsException.class)
      .hasMessage("Word index must be between 0 and 15");
  }

  @Test
  void handlesStringConversionCorrectly() {
    BigInteger value = new BigInteger("-89884656743115795386465259539451236680898848947115328636715040578866337902750481566354238661203768010560056939935696678829394884407208311246423715319737062188883946712432742638151109800623047059726541476042502884419075341171231440736956555270413618581675255342293149119973622969239858152417678164812112068608");
    I1024 i1024 = I1024.valueOf(value);

    assertThat(i1024.toString()).isEqualTo(value.toString());
  }

  @Test
  void handlesEqualityCorrectly() {
    long value = -123456789L;
    I1024 i1024a = I1024.valueOf(value);
    I1024 i1024b = I1024.valueOf(value);

    assertThat(i1024a.equals(i1024b)).isTrue();
    assertThat(i1024a.hashCode()).isEqualTo(i1024b.hashCode());
  }

  @Test
  void handlesUnequalValues() {
    I1024 i1024a = I1024.valueOf(123456789L);
    I1024 i1024b = I1024.valueOf(-987654321L);

    assertThat(i1024a.equals(i1024b)).isFalse();
  }

  @Test
  void handlesNullInEquals() {
    I1024 i1024 = I1024.valueOf(123456789L);

    assertThat(i1024.equals(null)).isFalse();
  }

  @Test
  void handlesDifferentClassInEquals() {
    I1024 i1024 = I1024.valueOf(123456789L);
    String other = "not an I1024";

    assertThat(i1024.equals(other)).isFalse();
  }

  @Test
  void handlesSelfEqualityInEquals() {
    I1024 i1024 = I1024.valueOf(-123456789L);

    assertThat(i1024.equals(i1024)).isTrue();
  }
}
