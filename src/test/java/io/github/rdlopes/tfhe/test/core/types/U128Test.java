package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.U128;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class U128Test {

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    U128 u128 = U128.valueOf(value);

    assertThat(u128).isNotNull();
    assertThat(u128.getAddress()).isNotNull();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    U128 u128 = U128.valueOf(originalValue);

    assertThat(u128).isEqualTo(originalValue);
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    U128 u128 = U128.valueOf(zero);

    assertThat(u128).isEqualTo(zero);
  }

  @Test
  void rejectsNegativeValueFromLong() {
    long negativeValue = -123456789L;

    assertThatThrownBy(() -> U128.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U128 cannot represent negative values");
  }

  @Test
  void rejectsNegativeValueFromString() {
    String negativeValue = "-123456789";

    assertThatThrownBy(() -> U128.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U128 cannot represent negative values");
  }

  @Test
  void rejectsNegativeValueFromBigInteger() {
    BigInteger negativeValue = BigInteger.valueOf(-123456789L);

    assertThatThrownBy(() -> U128.valueOf(negativeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("U128 cannot represent negative values");
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789";
    U128 u128 = U128.valueOf(largeValue);

    assertThat(u128).isEqualTo(largeValue);
  }

  @Test
  void handlesMaximumU128Value() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(128)
                                    .subtract(BigInteger.ONE);
    U128 u128 = U128.valueOf(maxValue);

    assertThat(u128).isEqualTo(maxValue);
  }

  @Test
  void rejectsValueExceeding128Bits() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(129);

    assertThatThrownBy(() -> U128.valueOf(tooLargeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds U128 maximum capacity (128 bits)");
  }

  @Test
  void createsFromComponents() {
    long w1 = 0x123456789ABCDEFL;
    long w0 = 0xFEDCBA9876543210L;

    U128 u128 = U128.valueOf(w1, w0);

    assertThat(u128).hasComponents(w1, w0);
  }

  @Test
  void retrievesComponentsCorrectly() {
    long w1 = 0x123456789ABCDEFL;
    long w0 = 0xFEDCBA9876543210L;

    U128 u128 = U128.valueOf(w1, w0);

    assertThat(u128.getW1()).isEqualTo(w1);
    assertThat(u128.getW0()).isEqualTo(w0);
  }

  @Test
  void handlesLargeValueWithCorrectComponents() {
    BigInteger value = BigInteger.valueOf(Long.MAX_VALUE)
                                 .shiftLeft(64)
                                 .add(BigInteger.valueOf(Long.MAX_VALUE));

    U128 u128 = U128.valueOf(value);

    assertThat(u128.getW1()).isEqualTo(Long.MAX_VALUE);
    assertThat(u128.getW0()).isEqualTo(Long.MAX_VALUE);
    assertThat(u128).isEqualTo(value);
  }

  @Test
  void handlesStringConversionCorrectly() {
    BigInteger value = new BigInteger("340282366920938463463374607431768211455");
    U128 u128 = U128.valueOf(value);

    assertThat(u128.toString()).isEqualTo(value.toString());
  }

  @Test
  void handlesEqualityCorrectly() {
    long value = 123456789L;
    U128 u128a = U128.valueOf(value);
    U128 u128b = U128.valueOf(value);

    assertThat(u128a.equals(u128b)).isTrue();
    assertThat(u128a.hashCode()).isEqualTo(u128b.hashCode());
  }

  @Test
  void handlesUnequalValues() {
    U128 u128a = U128.valueOf(123456789L);
    U128 u128b = U128.valueOf(987654321L);

    assertThat(u128a.equals(u128b)).isFalse();
  }

  @Test
  void handlesNullInEquals() {
    U128 u128 = U128.valueOf(123456789L);

    assertThat(u128.equals(null)).isFalse();
  }

  @Test
  void handlesDifferentClassInEquals() {
    U128 u128 = U128.valueOf(123456789L);
    String other = "not a U128";

    assertThat(u128.equals(other)).isFalse();
  }

  @Test
  void handlesSelfEqualityInEquals() {
    U128 u128 = U128.valueOf(123456789L);

    assertThat(u128.equals(u128)).isTrue();
  }
}
