package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.types.I2048;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

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

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(BigInteger.valueOf(originalValue));
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    I2048 i2048 = I2048.valueOf(zero);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(BigInteger.valueOf(zero));
  }

  @Test
  void handlesNegativeValue() {
    long negativeValue = -123456789L;
    I2048 i2048 = I2048.valueOf(negativeValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(BigInteger.valueOf(negativeValue));
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789012345678901234567890";
    I2048 i2048 = I2048.valueOf(largeValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(new BigInteger(largeValue));
  }

  @Test
  void handlesLargeNegativeValue() {
    String largeNegativeValue = "-123456789012345678901234567890123456789012345678901234567890";
    I2048 i2048 = I2048.valueOf(largeNegativeValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(new BigInteger(largeNegativeValue));
  }

  @Test
  void handlesMaximumValueRange() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(2047)
                                    .subtract(BigInteger.ONE);
    I2048 i2048 = I2048.valueOf(maxValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(maxValue);
  }

  @Test
  void handlesMinimumValueRange() {
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(2047)
                                    .negate();
    I2048 i2048 = I2048.valueOf(minValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(minValue);
  }
}
