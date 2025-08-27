package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.jca.TfhePrivateKey;
import io.github.rdlopes.tfhe.jca.TfhePublicKey;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigTest {

  @Test
  void generatesKeyPair() {
    Config config = new ConfigBuilder()
      .build();

    KeyPair keyPair = config.generateKeys();

    assertThat(keyPair).isNotNull();
    assertThat(keyPair.getPublic()).isNotNull();
    assertThat(keyPair.getPrivate()).isNotNull();
    assertThat(((TfhePublicKey) keyPair.getPublic()).serverKey()
                                                    .address()).isNotNull();
    assertThat(((TfhePrivateKey) keyPair.getPrivate()).clientKey()
                                                      .address()).isNotNull();
  }

  @Test
  void generatesClientKey() {
    Config config = new ConfigBuilder()
      .build();

    ClientKey clientKey = config.generateClientKey();

    assertThat(clientKey).isNotNull();
    assertThat(clientKey.address()).isNotNull();
  }

  @Test
  void doesNotGenerateMultipleClientKeys() {
    Config config = new ConfigBuilder()
      .build();

    ClientKey clientKey = config.generateClientKey();
    assertThat(clientKey).isNotNull();
    assertThat(clientKey.address()).isNotNull();

    assertThatThrownBy(config::generateClientKey)
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("Keys have already been generated for this Config instance");
  }

  @Test
  void doesNotGenerateMultipleKeyPairs() {
    Config config = new ConfigBuilder()
      .build();

    KeyPair keyPair1 = config.generateKeys();

    assertThat(keyPair1).isNotNull();
    assertThat(keyPair1.getPublic()).isNotNull();
    assertThat(keyPair1.getPrivate()).isNotNull();

    assertThatThrownBy(config::generateKeys)
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("Keys have already been generated for this Config instance");
  }
}
