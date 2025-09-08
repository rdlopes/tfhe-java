package io.github.rdlopes.tfhe.test.api.keys;

import io.github.rdlopes.tfhe.api.config.Config;
import io.github.rdlopes.tfhe.api.config.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class PublicKeyTest {
  private ClientKey clientKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
  }

  @Test
  void throwsOnSerialize() {
    PublicKey publicKey = PublicKey.newWith(clientKey);
    assertThatCode(publicKey::serialize)
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessageFindingMatch("Buffer length \\d+ exceeds maximum length \\d+");
  }
}
