package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.I256;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class I256Test {

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    I256 i256 = I256.valueOf(value);

    assertThat(i256).isNotNull();
    assertThat(i256.getAddress()).isNotNull();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    I256 i256 = I256.valueOf(originalValue);

    assertThat(i256).isEqualTo(originalValue);
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    I256 i256 = I256.valueOf(zero);

    assertThat(i256).isEqualTo(zero);
  }

  @Test
  void handlesNegativeValue() {
    long negativeValue = -123456789L;
    I256 i256 = I256.valueOf(negativeValue);

    assertThat(i256).isEqualTo(negativeValue);
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789012345678901234567890";
    I256 i256 = I256.valueOf(largeValue);

    assertThat(i256).isEqualTo(largeValue);
  }

  @Test
  void handlesLargeNegativeValue() {
    String largeNegativeValue = "-123456789012345678901234567890123456789012345678901234567890";
    I256 i256 = I256.valueOf(largeNegativeValue);

    assertThat(i256).isEqualTo(largeNegativeValue);
  }

  @Test
  void handlesMaximumValueRange() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(255)
                                    .subtract(BigInteger.ONE);
    I256 i256 = I256.valueOf(maxValue);

    assertThat(i256).isEqualTo(maxValue);
  }

  @Test
  void handlesMinimumValueRange() {
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(255)
                                    .negate();
    I256 i256 = I256.valueOf(minValue);

    assertThat(i256).isEqualTo(minValue);
  }

  @Test
  void rejectsValueExceedingMaximumCapacity() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(255);

    assertThatThrownBy(() -> I256.valueOf(tooLargeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I256 maximum capacity (2^255 - 1)");
  }

  @Test
  void rejectsValueExceedingMinimumCapacity() {
    BigInteger tooSmallValue = BigInteger.valueOf(2)
                                         .pow(255)
                                         .negate()
                                         .subtract(BigInteger.ONE);

    assertThatThrownBy(() -> I256.valueOf(tooSmallValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I256 minimum capacity (-2^255)");
  }

  @Test
  void createsFromComponents() {
    long w3 = 0x123456789ABCDEFL;
    long w2 = 0xFEDCBA9876543210L;
    long w1 = 0x1111222233334444L;
    long w0 = 0x5555666677778888L;

    I256 i256 = I256.valueOf(w3, w2, w1, w0);

    assertThat(i256).hasComponents(w3, w2, w1, w0);
  }

  @Test
  void retrievesComponentsCorrectly() {
    long w3 = 0x123456789ABCDEFL;
    long w2 = 0xFEDCBA9876543210L;
    long w1 = 0x1111222233334444L;
    long w0 = 0x5555666677778888L;

    I256 i256 = I256.valueOf(w3, w2, w1, w0);

    assertThat(i256.getW3()).isEqualTo(w3);
    assertThat(i256.getW2()).isEqualTo(w2);
    assertThat(i256.getW1()).isEqualTo(w1);
    assertThat(i256.getW0()).isEqualTo(w0);
  }

  @Test
  void handlesStringConversionCorrectly() {
    BigInteger value = new BigInteger("-57896044618658097711785492504343953926634992332820282019728792003956564819968");
    I256 i256 = I256.valueOf(value);

    assertThat(i256.toString()).isEqualTo(value.toString());
  }

  @Test
  void handlesEqualityCorrectly() {
    long value = -123456789L;
    I256 i256a = I256.valueOf(value);
    I256 i256b = I256.valueOf(value);

    assertThat(i256a.equals(i256b)).isTrue();
    assertThat(i256a.hashCode()).isEqualTo(i256b.hashCode());
  }

  @Test
  void handlesUnequalValues() {
    I256 i256a = I256.valueOf(123456789L);
    I256 i256b = I256.valueOf(-987654321L);

    assertThat(i256a.equals(i256b)).isFalse();
  }

  @Test
  void handlesNullInEquals() {
    I256 i256 = I256.valueOf(123456789L);

    assertThat(i256.equals(null)).isFalse();
  }

  @Test
  void handlesDifferentClassInEquals() {
    I256 i256 = I256.valueOf(123456789L);
    String other = "not an I256";

    assertThat(i256.equals(other)).isFalse();
  }

  @Test
  void handlesSelfEqualityInEquals() {
    I256 i256 = I256.valueOf(-123456789L);

    assertThat(i256.equals(i256)).isTrue();
  }
}
