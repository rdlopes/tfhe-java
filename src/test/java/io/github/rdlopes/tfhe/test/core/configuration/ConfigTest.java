package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.jca.TfhePrivateKey;
import io.github.rdlopes.tfhe.jca.TfhePublicKey;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConfigTest {

  @Test
  void testGenerateKeyPair() {
    // Create Config object with default parameters
    Config config = new ConfigBuilder()
      .build();

    // Generate KeyPair from Config
    KeyPair keyPair = config.generateKeys();

    // Verify KeyPair generation
    assertThat(keyPair).isNotNull();
    assertThat(keyPair.getPublic()).isNotNull();
    assertThat(keyPair.getPrivate()).isNotNull();
    assertThat(((TfhePublicKey) keyPair.getPublic()).serverKey()
                                                    .address()).isNotNull();
    assertThat(((TfhePrivateKey) keyPair.getPrivate()).clientKey()
                                                      .address()).isNotNull();
  }

  @Test
  void testGenerateMultipleKeyPairs() {
    // Create Config object with default parameters
    Config config = new ConfigBuilder()
      .build();

    // Generate first KeyPair successfully
    KeyPair keyPair1 = config.generateKeys();

    // Verify first KeyPair generation
    assertThat(keyPair1).isNotNull();
    assertThat(keyPair1.getPublic()).isNotNull();
    assertThat(keyPair1.getPrivate()).isNotNull();

    // Attempt to generate second KeyPair should throw exception
    assertThatThrownBy(config::generateKeys)
      .isInstanceOf(IllegalStateException.class)
      .hasMessage("Keys have already been generated for this Config instance");
  }
}
