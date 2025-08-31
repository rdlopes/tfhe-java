package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigTest {
  private ConfigBuilder configBuilder;
  private Config config;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
  }

  @AfterEach
  void tearDown() {
    configBuilder.cleanNativeResources();
    config.cleanNativeResources();
  }

  @Test
  void generatesKeyPair() {
    KeySet keySet = config.generateKeys();

    assertThat(keySet).isNotNull();
    assertThat(keySet.clientKey()).isNotNull();
    assertThat(keySet.serverKey()).isNotNull();

    keySet.clientKey()
          .cleanNativeResources();
    keySet.serverKey()
          .cleanNativeResources();
  }

  @Test
  void generatesClientKey() {
    ClientKey clientKey = config.generateClientKey();

    assertThat(clientKey).isNotNull();
    assertThat(clientKey.getAddress()).isNotNull();

    clientKey.cleanNativeResources();
  }

  @Test
  void doesNotGenerateMultipleClientKeys() {
    ClientKey clientKey = config.generateClientKey();

    assertThatThrownBy(config::generateClientKey)
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("Keys have already been generated for this Config instance");

    clientKey.cleanNativeResources();
  }

  @Test
  void doesNotGenerateMultipleKeyPairs() {
    KeySet keySet = config.generateKeys();

    assertThatThrownBy(config::generateKeys)
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("Keys have already been generated for this Config instance");

    keySet.clientKey()
          .cleanNativeResources();
    keySet.serverKey()
          .cleanNativeResources();
  }
}
