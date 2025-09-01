package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigTest {
  private Config config;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    config = configBuilder.build();
  }


  @Test
  void generatesKeyPair() {
    KeySet keySet = config.generateKeys();

    assertThat(keySet).isNotNull();
    assertThat(keySet.clientKey()).isNotNull();
    assertThat(keySet.serverKey()).isNotNull();
  }

  @Test
  void generatesClientKey() {
    ClientKey clientKey = config.generateClientKey();

    assertThat(clientKey).isNotNull();
    assertThat(clientKey.getAddress()).isNotNull();
  }

  @Test
  void doesNotGenerateMultipleClientKeys() {
    config.generateClientKey();

    assertThatThrownBy(config::generateClientKey)
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("Keys have already been generated for this Config instance");
  }

  @Test
  void doesNotGenerateMultipleKeyPairs() {
    config.generateKeys();

    assertThatThrownBy(config::generateKeys)
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("Keys have already been generated for this Config instance");
  }
}
