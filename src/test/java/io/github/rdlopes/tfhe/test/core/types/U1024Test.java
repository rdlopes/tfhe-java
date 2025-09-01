package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.U1024;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class U1024Test {

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    U1024 u1024 = U1024.valueOf(value);

    assertThat(u1024).isNotNull();
    assertThat(u1024.getAddress()).isNotNull();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    U1024 u1024 = U1024.valueOf(originalValue);

    assertThat(u1024).isEqualTo(originalValue);
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    U1024 u1024 = U1024.valueOf(zero);

    assertThat(u1024).isEqualTo(zero);
  }

  @Test
  void rejectsNegativeValueFromLong() {
    long negativeValue = -123456789L;

    assertThatThrownBy(() -> U1024.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U1024 cannot represent negative values");
  }

  @Test
  void rejectsNegativeValueFromString() {
    String negativeValue = "-123456789";

    assertThatThrownBy(() -> U1024.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U1024 cannot represent negative values");
  }

  @Test
  void rejectsNegativeValueFromBigInteger() {
    BigInteger negativeValue = BigInteger.valueOf(-123456789L);

    assertThatThrownBy(() -> U1024.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U1024 cannot represent negative values");
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789012345678901234567890";
    U1024 u1024 = U1024.valueOf(largeValue);

    assertThat(u1024).isEqualTo(largeValue);
  }

  @Test
  void handlesMaximumU1024Value() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(1024)
                                    .subtract(BigInteger.ONE);
    U1024 u1024 = U1024.valueOf(maxValue);

    assertThat(u1024).isEqualTo(maxValue);
  }

  @Test
  void rejectsValueExceeding1024Bits() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(1025);

    assertThatThrownBy(() -> U1024.valueOf(tooLargeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds U1024 maximum capacity (1024 bits)");
  }

  @Test
  void createsFromWordArray() {
    long[] words = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L,
      0x5555555566666666L, 0x7777777788888888L, 0x9999999900000000L, 0xAAAABBBBCCCCDDDDL,
      0xEEEEFFFF11112222L, 0x3333444455556666L, 0x7777888899990000L, 0xAAAABBBBCCCCDDDDL};

    U1024 u1024 = U1024.valueOf(words);

    assertThat(u1024).hasWords(words);
  }

  @Test
  void retrievesWordArrayCorrectly() {
    long[] originalWords = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L, 0x1111222233334444L, 0x5555666677778888L,
      0x9999AAAABBBBCCCL, 0xDDDDEEEEFFFF0000L, 0x1111111122222222L, 0x3333333344444444L,
      0x5555555566666666L, 0x7777777788888888L, 0x9999999900000000L, 0xAAAABBBBCCCCDDDDL,
      0xEEEEFFFF11112222L, 0x3333444455556666L, 0x7777888899990000L, 0xAAAABBBBCCCCDDDDL};

    U1024 u1024 = U1024.valueOf(originalWords);
    long[] retrievedWords = u1024.getWords();

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

    U1024 u1024 = U1024.valueOf(words);

    for (int i = 0; i < 16; i++) {
      assertThat(u1024.getWord(i)).isEqualTo(words[i]);
    }
  }

  @Test
  void rejectsInvalidWordArrayLength() {
    long[] invalidWords = {0x1234567890ABCDEFL, 0xFEDCBA0987654321L};

    assertThatThrownBy(() -> U1024.valueOf(invalidWords))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U1024 requires exactly 16 words");
  }

  @Test
  void rejectsInvalidWordIndex() {
    U1024 u1024 = U1024.valueOf(12345L);

    assertThatThrownBy(() -> u1024.getWord(-1))
      .isInstanceOf(IndexOutOfBoundsException.class)
      .hasMessage("Word index must be between 0 and 15");

    assertThatThrownBy(() -> u1024.getWord(16))
      .isInstanceOf(IndexOutOfBoundsException.class)
      .hasMessage("Word index must be between 0 and 15");
  }

  @Test
  void handlesLargeValueWithWordArray() {
    BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
    for (int i = 1; i < 16; i++) {
      value = value.add(BigInteger.valueOf(Long.MAX_VALUE)
                                  .shiftLeft(i * 64));
    }

    U1024 u1024 = U1024.valueOf(value);

    for (int i = 0; i < 16; i++) {
      assertThat(u1024.getWord(i)).isEqualTo(Long.MAX_VALUE);
    }
    assertThat(u1024).isEqualTo(value);
  }

  @Test
  void handlesStringConversionCorrectly() {
    BigInteger value = new BigInteger("89884656743115795386465259539451236680898848947115328636715040578866337902750481566354238661203768010560056939935696678829394884407208311246423715319737062188883946712432742638151109800623047059726541476042502884419075341171231440736956555270413618581675255342293149119973622969239858152417678164812112068608");
    U1024 u1024 = U1024.valueOf(value);

    assertThat(u1024.toString()).isEqualTo(value.toString());
  }

  @Test
  void handlesEqualityCorrectly() {
    long value = 123456789L;
    U1024 u1024a = U1024.valueOf(value);
    U1024 u1024b = U1024.valueOf(value);

    assertThat(u1024a.equals(u1024b)).isTrue();
    assertThat(u1024a.hashCode()).isEqualTo(u1024b.hashCode());
  }

  @Test
  void handlesUnequalValues() {
    U1024 u1024a = U1024.valueOf(123456789L);
    U1024 u1024b = U1024.valueOf(987654321L);

    assertThat(u1024a.equals(u1024b)).isFalse();
  }

  @Test
  void handlesNullInEquals() {
    U1024 u1024 = U1024.valueOf(123456789L);

    assertThat(u1024.equals(null)).isFalse();
  }

  @Test
  void handlesDifferentClassInEquals() {
    U1024 u1024 = U1024.valueOf(123456789L);
    String other = "not a U1024";

    assertThat(u1024.equals(other)).isFalse();
  }

  @Test
  void handlesSelfEqualityInEquals() {
    U1024 u1024 = U1024.valueOf(123456789L);

    assertThat(u1024.equals(u1024)).isTrue();
  }
}
