package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.U256;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class U256Test {

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    U256 u256 = U256.valueOf(value);

    assertThat(u256).isNotNull();
    assertThat(u256.getAddress()).isNotNull();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    U256 u256 = U256.valueOf(originalValue);

    assertThat(u256).isEqualTo(originalValue);
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    U256 u256 = U256.valueOf(zero);

    assertThat(u256).isEqualTo(zero);
  }

  @Test
  void rejectsNegativeValueFromLong() {
    long negativeValue = -123456789L;

    assertThatThrownBy(() -> U256.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U256 cannot represent negative values");
  }

  @Test
  void rejectsNegativeValueFromString() {
    String negativeValue = "-123456789";

    assertThatThrownBy(() -> U256.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U256 cannot represent negative values");
  }

  @Test
  void rejectsNegativeValueFromBigInteger() {
    BigInteger negativeValue = BigInteger.valueOf(-123456789L);

    assertThatThrownBy(() -> U256.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U256 cannot represent negative values");
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789012345678901234567890";
    U256 u256 = U256.valueOf(largeValue);

    assertThat(u256).isEqualTo(largeValue);
  }

  @Test
  void handlesMaximumU256Value() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(256)
                                    .subtract(BigInteger.ONE);
    U256 u256 = U256.valueOf(maxValue);

    assertThat(u256).isEqualTo(maxValue);
  }

  @Test
  void rejectsValueExceeding256Bits() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(257);

    assertThatThrownBy(() -> U256.valueOf(tooLargeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds U256 maximum capacity (256 bits)");
  }

  @Test
  void createsFromComponents() {
    long w3 = 0x123456789ABCDEFL;
    long w2 = 0xFEDCBA9876543210L;
    long w1 = 0x1111222233334444L;
    long w0 = 0x5555666677778888L;

    U256 u256 = U256.valueOf(w3, w2, w1, w0);

    assertThat(u256).hasComponents(w3, w2, w1, w0);
  }

  @Test
  void retrievesComponentsCorrectly() {
    long w3 = 0x123456789ABCDEFL;
    long w2 = 0xFEDCBA9876543210L;
    long w1 = 0x1111222233334444L;
    long w0 = 0x5555666677778888L;

    U256 u256 = U256.valueOf(w3, w2, w1, w0);

    assertThat(u256.getW3()).isEqualTo(w3);
    assertThat(u256.getW2()).isEqualTo(w2);
    assertThat(u256.getW1()).isEqualTo(w1);
    assertThat(u256.getW0()).isEqualTo(w0);
  }

  @Test
  void handlesLargeValueWithCorrectComponents() {
    BigInteger value = BigInteger.valueOf(Long.MAX_VALUE)
                                 .shiftLeft(192)
                                 .add(BigInteger.valueOf(Long.MAX_VALUE)
                                                .shiftLeft(128))
                                 .add(BigInteger.valueOf(Long.MAX_VALUE)
                                                .shiftLeft(64))
                                 .add(BigInteger.valueOf(Long.MAX_VALUE));

    U256 u256 = U256.valueOf(value);

    assertThat(u256.getW3()).isEqualTo(Long.MAX_VALUE);
    assertThat(u256.getW2()).isEqualTo(Long.MAX_VALUE);
    assertThat(u256.getW1()).isEqualTo(Long.MAX_VALUE);
    assertThat(u256.getW0()).isEqualTo(Long.MAX_VALUE);
    assertThat(u256).isEqualTo(value);
  }

  @Test
  void handlesStringConversionCorrectly() {
    BigInteger value = new BigInteger("115792089237316195423570985008687907853269984665640564039457584007913129639935");
    U256 u256 = U256.valueOf(value);

    assertThat(u256.toString()).isEqualTo(value.toString());
  }

  @Test
  void handlesEqualityCorrectly() {
    long value = 123456789L;
    U256 u256a = U256.valueOf(value);
    U256 u256b = U256.valueOf(value);

    assertThat(u256a.equals(u256b)).isTrue();
    assertThat(u256a.hashCode()).isEqualTo(u256b.hashCode());
  }

  @Test
  void handlesUnequalValues() {
    U256 u256a = U256.valueOf(123456789L);
    U256 u256b = U256.valueOf(987654321L);

    assertThat(u256a.equals(u256b)).isFalse();
  }

  @Test
  void handlesNullInEquals() {
    U256 u256 = U256.valueOf(123456789L);

    assertThat(u256.equals(null)).isFalse();
  }

  @Test
  void handlesDifferentClassInEquals() {
    U256 u256 = U256.valueOf(123456789L);
    String other = "not a U256";

    assertThat(u256.equals(other)).isFalse();
  }

  @Test
  void handlesSelfEqualityInEquals() {
    U256 u256 = U256.valueOf(123456789L);

    assertThat(u256.equals(u256)).isTrue();
  }
}
