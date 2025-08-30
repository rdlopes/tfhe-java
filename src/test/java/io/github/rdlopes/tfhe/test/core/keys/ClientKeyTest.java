package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientKeyTest {

  private ConfigBuilder configBuilder;
  private Config config;
  private ClientKey clientKey;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
    clientKey = config.generateClientKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    config.destroy();
    configBuilder.destroy();
  }

  @Test
  void serializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = clientKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    ClientKey deserializedClientKey = new ClientKey();
    deserializedClientKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedClientKey).isNotNull();
    assertThat(deserializedClientKey.getAddress()).isNotNull();

    deserializedClientKey.destroy();
    dynamicBuffer.destroy();
  }
}
