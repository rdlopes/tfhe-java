package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.I2048;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class I2048Test {

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    I2048 i2048 = I2048.valueOf(value);

    assertThat(i2048).isNotNull();
    assertThat(i2048.getAddress()).isNotNull();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    I2048 i2048 = I2048.valueOf(originalValue);

    assertThat(i2048).isEqualTo(originalValue);
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    I2048 i2048 = I2048.valueOf(zero);

    assertThat(i2048).isEqualTo(zero);
  }

  @Test
  void handlesNegativeValue() {
    long negativeValue = -123456789L;
    I2048 i2048 = I2048.valueOf(negativeValue);

    assertThat(i2048).isEqualTo(negativeValue);
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789012345678901234567890";
    I2048 i2048 = I2048.valueOf(largeValue);

    assertThat(i2048).isEqualTo(largeValue);
  }

  @Test
  void handlesLargeNegativeValue() {
    String largeNegativeValue = "-123456789012345678901234567890123456789012345678901234567890";
    I2048 i2048 = I2048.valueOf(largeNegativeValue);

    assertThat(i2048).isEqualTo(largeNegativeValue);
  }

  @Test
  void handlesMaximumValueRange() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(2047)
                                    .subtract(BigInteger.ONE);
    I2048 i2048 = I2048.valueOf(maxValue);

    assertThat(i2048).isEqualTo(maxValue);
  }

  @Test
  void handlesMinimumValueRange() {
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(2047)
                                    .negate();
    I2048 i2048 = I2048.valueOf(minValue);

    assertThat(i2048).isEqualTo(minValue);
  }

  @Test
  void rejectsValueExceedingMaximumCapacity() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(2047);

    assertThatThrownBy(() -> I2048.valueOf(tooLargeValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I2048 maximum capacity (2^2047 - 1)");
  }

  @Test
  void rejectsValueExceedingMinimumCapacity() {
    BigInteger tooSmallValue = BigInteger.valueOf(2)
                                         .pow(2047)
                                         .negate()
                                         .subtract(BigInteger.ONE);

    assertThatThrownBy(() -> I2048.valueOf(tooSmallValue))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I2048 minimum capacity (-2^2047)");
  }

  @Test
  void rejectsStringValueExceedingMaximumCapacity() {
    BigInteger tooLargeValue = BigInteger.valueOf(2)
                                         .pow(2047);

    assertThatThrownBy(() -> I2048.valueOf(tooLargeValue.toString()))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I2048 maximum capacity (2^2047 - 1)");
  }

  @Test
  void rejectsStringValueExceedingMinimumCapacity() {
    BigInteger tooSmallValue = BigInteger.valueOf(2)
                                         .pow(2047)
                                         .negate()
                                         .subtract(BigInteger.ONE);

    assertThatThrownBy(() -> I2048.valueOf(tooSmallValue.toString()))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Value exceeds I2048 minimum capacity (-2^2047)");
  }
}
