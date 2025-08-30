package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.NativeCallException;
import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class PublicKeyTest {

  private ConfigBuilder configBuilder;
  private Config config;
  private ClientKey clientKey;
  private PublicKey publicKey;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
    clientKey = config.generateClientKey();
    publicKey = clientKey.newPublicKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    publicKey.destroy();
    configBuilder.destroy();
    config.destroy();
  }

  @Test
  @Tag("largeByteBuffer")
  void serializesAndDeserializes() {
    assertThatCode(() -> publicKey.serialize())
      .isInstanceOf(NativeCallException.class);
  }
}
