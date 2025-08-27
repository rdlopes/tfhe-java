package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.jca.TfhePublicKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

class ServerKeyTest {

  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    Config config = new ConfigBuilder().build();
    KeyPair keyPair = config.generateKeys();
    TfhePublicKey publicKey = (TfhePublicKey) keyPair.getPublic();
    serverKey = publicKey.serverKey();
  }

  @Test
  void serializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = serverKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.pointer()).isNotNull();
    assertThat(dynamicBuffer.length()).isGreaterThan(0);

    ServerKey deserializedServerKey = ServerKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedServerKey).isNotNull();
    assertThat(deserializedServerKey.address()).isNotNull();
  }

  @Test
  void safeSerializes() {
    DynamicBuffer dynamicBuffer = serverKey.safeSerialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.pointer()).isNotNull();
    assertThat(dynamicBuffer.length()).isGreaterThan(0);
  }

  @Test
  void destroysSuccessfully() {
    assertThat(serverKey.address()).isNotNull();

    serverKey.destroy();
  }
}
