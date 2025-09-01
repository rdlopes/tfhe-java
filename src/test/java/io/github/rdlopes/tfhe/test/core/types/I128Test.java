package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.I128;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class I128Test {

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    I128 i128 = I128.valueOf(value);

    assertThat(i128).isNotNull();
    assertThat(i128.getAddress()).isNotNull();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    I128 i128 = I128.valueOf(originalValue);

    assertThat(i128).isEqualTo(originalValue);
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    I128 i128 = I128.valueOf(zero);

    assertThat(i128).isEqualTo(zero);
  }

  @Test
  void handlesNegativeValue() {
    long negativeValue = -123456789L;
    I128 i128 = I128.valueOf(negativeValue);

    assertThat(i128).isEqualTo(negativeValue);
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789";
    I128 i128 = I128.valueOf(largeValue);

    assertThat(i128).isEqualTo(largeValue);
  }

  @Test
  void handlesLargeNegativeValue() {
    String largeNegativeValue = "-123456789012345678901234567890123456789";
    I128 i128 = I128.valueOf(largeNegativeValue);

    assertThat(i128).isEqualTo(largeNegativeValue);
  }

  @Test
  void handlesMaximumValueRange() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(127)
                                    .subtract(BigInteger.ONE);
    I128 i128 = I128.valueOf(maxValue);

    assertThat(i128).isEqualTo(maxValue);
  }

  @Test
  void handlesMinimumValueRange() {
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(127)
                                    .negate();
    I128 i128 = I128.valueOf(minValue);

    assertThat(i128).isEqualTo(minValue);
  }

  @Test
  void rejectsValueExceedingMaximumCapacity() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(127);

    assertThatThrownBy(() -> I128.valueOf(tooLargeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I128 maximum capacity (2^127 - 1)");
  }

  @Test
  void rejectsValueExceedingMinimumCapacity() {
    BigInteger tooSmallValue = BigInteger.valueOf(2)
                                         .pow(127)
                                         .negate()
                                         .subtract(BigInteger.ONE);

    assertThatThrownBy(() -> I128.valueOf(tooSmallValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I128 minimum capacity (-2^127)");
  }

  @Test
  void rejectsStringValueExceedingMaximumCapacity() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(127);

    assertThatThrownBy(() -> I128.valueOf(tooLargeValue.toString()))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I128 maximum capacity (2^127 - 1)");
  }

  @Test
  void rejectsStringValueExceedingMinimumCapacity() {
    BigInteger tooSmallValue = BigInteger.valueOf(2)
                                         .pow(127)
                                         .negate()
                                         .subtract(BigInteger.ONE);

    assertThatThrownBy(() -> I128.valueOf(tooSmallValue.toString()))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I128 minimum capacity (-2^127)");
  }

  @Test
  void createsFromComponents() {
    long w1 = 0x123456789ABCDEFL;
    long w0 = 0xFEDCBA9876543210L;

    I128 i128 = I128.valueOf(w1, w0);

    assertThat(i128).hasComponents(w1, w0);
  }

  @Test
  void retrievesComponentsCorrectly() {
    long w1 = 0x123456789ABCDEFL;
    long w0 = 0xFEDCBA9876543210L;

    I128 i128 = I128.valueOf(w1, w0);

    assertThat(i128.getW1()).isEqualTo(w1);
    assertThat(i128.getW0()).isEqualTo(w0);
  }

  @Test
  void handlesStringConversionCorrectly() {
    BigInteger value = new BigInteger("-170141183460469231731687303715884105728");
    I128 i128 = I128.valueOf(value);

    assertThat(i128.toString()).isEqualTo(value.toString());
  }

  @Test
  void handlesEqualityCorrectly() {
    long value = -123456789L;
    I128 i128a = I128.valueOf(value);
    I128 i128b = I128.valueOf(value);

    assertThat(i128a.equals(i128b)).isTrue();
    assertThat(i128a.hashCode()).isEqualTo(i128b.hashCode());
  }

  @Test
  void handlesUnequalValues() {
    I128 i128a = I128.valueOf(123456789L);
    I128 i128b = I128.valueOf(-987654321L);

    assertThat(i128a.equals(i128b)).isFalse();
  }

  @Test
  void handlesNullInEquals() {
    I128 i128 = I128.valueOf(123456789L);

    assertThat(i128.equals(null)).isFalse();
  }

  @Test
  void handlesDifferentClassInEquals() {
    I128 i128 = I128.valueOf(123456789L);
    String other = "not an I128";

    assertThat(i128.equals(other)).isFalse();
  }

  @Test
  void handlesSelfEqualityInEquals() {
    I128 i128 = I128.valueOf(-123456789L);

    assertThat(i128.equals(i128)).isTrue();
  }
}
