package io.github.rdlopes.tfhe.test.api.keys;

import io.github.rdlopes.tfhe.api.config.Config;
import io.github.rdlopes.tfhe.api.config.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientKeyTest {
  private ClientKey clientKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    clientKey = config.generateClientKey();
  }

  @Test
  void serializesAndDeserializes() {
    byte[] buffer = clientKey.serialize();
    ClientKey deserialized = ClientKey.deserialize(buffer);

    assertThat(deserialized.getAddress()).isNotNull();
  }
}
