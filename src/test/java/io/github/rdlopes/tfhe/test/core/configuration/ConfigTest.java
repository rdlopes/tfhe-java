package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigTest {

  @Test
  void generatesKeyPair() {
    Config config = new ConfigBuilder()
      .build();

    KeySet keySet = config.generateKeys();

    assertThat(keySet).isNotNull();
    assertThat(keySet.clientKey()).isNotNull();
    assertThat(keySet.serverKey()).isNotNull();
  }

  @Test
  void generatesClientKey() {
    Config config = new ConfigBuilder()
      .build();

    ClientKey clientKey = config.generateClientKey();

    assertThat(clientKey).isNotNull();
    assertThat(clientKey.getAddress()).isNotNull();
  }

  @Test
  void doesNotGenerateMultipleClientKeys() {
    Config config = new ConfigBuilder()
      .build();

    ClientKey clientKey = config.generateClientKey();
    assertThat(clientKey).isNotNull();
    assertThat(clientKey.getAddress()).isNotNull();

    assertThatThrownBy(config::generateClientKey)
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("Keys have already been generated for this Config instance");
  }

  @Test
  void doesNotGenerateMultipleKeyPairs() {
    Config config = new ConfigBuilder()
      .build();

    KeySet keySet = config.generateKeys();

    assertThat(keySet).isNotNull();
    assertThat(keySet.clientKey()).isNotNull();
    assertThat(keySet.serverKey()).isNotNull();

    assertThatThrownBy(config::generateKeys)
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("Keys have already been generated for this Config instance");
  }
}
