package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ServerKeyTest {

  private ConfigBuilder configBuilder;
  private Config config;
  private ClientKey clientKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    serverKey.destroy();
    config.destroy();
    configBuilder.destroy();
  }

  @Test
  void serializes() {
    DynamicBuffer dynamicBuffer = serverKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    dynamicBuffer.destroy();
  }

  @Test
  void doesNotDeserialize() {
    DynamicBuffer dynamicBuffer = serverKey.serialize();
    assertThatCode(() -> serverKey.deserialize(dynamicBuffer.view()))
      .isInstanceOf(UnsupportedOperationException.class);

    dynamicBuffer.destroy();
  }
}
