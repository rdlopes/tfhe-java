package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ServerKeyTest {

  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    Config config = new ConfigBuilder().build();
    KeySet keySet = config.generateKeys();
    serverKey = keySet.serverKey();
  }

  @Test
  void serializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = serverKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    ServerKey deserializedServerKey = ServerKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedServerKey).isNotNull();
    assertThat(deserializedServerKey.getAddress()).isNotNull();
  }

  @Test
  void safeSerializes() {
    DynamicBuffer dynamicBuffer = serverKey.safeSerialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);
  }

  @Test
  void destroysSuccessfully() {
    assertThat(serverKey.getAddress()).isNotNull();

    serverKey.destroy();
  }
}
