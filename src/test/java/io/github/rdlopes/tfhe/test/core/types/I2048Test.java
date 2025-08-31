package io.github.rdlopes.tfhe.test.core.types;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.types.I2048;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

class I2048Test {
  private ConfigBuilder configBuilder;
  private Config config;
  private ClientKey clientKey;
  private ServerKey serverKey;
  private PublicKey publicKey;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();

    serverKey.setAsKey();

    publicKey = PublicKey.newWith(clientKey);
  }

  @AfterEach
  void tearDown() {
    configBuilder.cleanNativeResources();
    config.cleanNativeResources();
    clientKey.cleanNativeResources();
    serverKey.cleanNativeResources();
    publicKey.cleanNativeResources();
  }

  @Test
  void createsValueOf() {
    long value = 12345678901234567L;
    I2048 i2048 = I2048.valueOf(value);

    assertThat(i2048).isNotNull();
    assertThat(i2048.getAddress()).isNotNull();

    i2048.cleanNativeResources();
  }

  @Test
  void retrievesValueCorrectly() {
    long originalValue = 98765432109876543L;
    I2048 i2048 = I2048.valueOf(originalValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(BigInteger.valueOf(originalValue));

    i2048.cleanNativeResources();
  }

  @Test
  void handlesZeroValue() {
    long zero = 0L;
    I2048 i2048 = I2048.valueOf(zero);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(BigInteger.valueOf(zero));

    i2048.cleanNativeResources();
  }

  @Test
  void handlesNegativeValue() {
    long negativeValue = -123456789L;
    I2048 i2048 = I2048.valueOf(negativeValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(BigInteger.valueOf(negativeValue));

    i2048.cleanNativeResources();
  }

  @Test
  void handlesLargePositiveValue() {
    String largeValue = "123456789012345678901234567890123456789012345678901234567890";
    I2048 i2048 = I2048.valueOf(largeValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(new BigInteger(largeValue));

    i2048.cleanNativeResources();
  }

  @Test
  void handlesLargeNegativeValue() {
    String largeNegativeValue = "-123456789012345678901234567890123456789012345678901234567890";
    I2048 i2048 = I2048.valueOf(largeNegativeValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(new BigInteger(largeNegativeValue));

    i2048.cleanNativeResources();
  }

  @Test
  void handlesMaximumValueRange() {
    BigInteger maxValue = BigInteger.valueOf(2)
                                    .pow(2047)
                                    .subtract(BigInteger.ONE);
    I2048 i2048 = I2048.valueOf(maxValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(maxValue);

    i2048.cleanNativeResources();
  }

  @Test
  void handlesMinimumValueRange() {
    BigInteger minValue = BigInteger.valueOf(2)
                                    .pow(2047)
                                    .negate();
    I2048 i2048 = I2048.valueOf(minValue);

    BigInteger retrievedValue = i2048.getValue();
    assertThat(retrievedValue).isEqualTo(minValue);

    i2048.cleanNativeResources();
  }
}
