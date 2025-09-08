package io.github.rdlopes.tfhe.test.api.keys;

import io.github.rdlopes.tfhe.api.config.Config;
import io.github.rdlopes.tfhe.api.config.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ServerKeyTest {
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    serverKey = keySet.serverKey();
    serverKey.setAsKey();
  }

  @Test
  void serializes() {
    byte[] buffer = serverKey.serialize();

    assertThat(buffer.length).isGreaterThan(0);
  }

  @Test
  void doesNotDeserialize() {
    byte[] buffer = serverKey.serialize();

    assertThatCode(() -> ServerKey.deserialize(buffer))
      .isInstanceOf(UnsupportedOperationException.class);
  }
}
