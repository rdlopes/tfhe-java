package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.jca.TfhePrivateKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

class ClientKeyTest {

  private ClientKey clientKey;

  @BeforeEach
  void setUp() {
    Config config = new ConfigBuilder().build();
    KeyPair keyPair = config.generateKeys();
    TfhePrivateKey privateKey = (TfhePrivateKey) keyPair.getPrivate();
    clientKey = privateKey.clientKey();
  }

  @Test
  void serializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = clientKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.pointer()).isNotNull();
    assertThat(dynamicBuffer.length()).isGreaterThan(0);

    ClientKey deserializedClientKey = ClientKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedClientKey).isNotNull();
    assertThat(deserializedClientKey.address()).isNotNull();
  }

  @Test
  void safeSerializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = clientKey.safeSerialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.pointer()).isNotNull();
    assertThat(dynamicBuffer.length()).isGreaterThan(0);

    ClientKey deserializedClientKey = ClientKey.safeDeserialize(dynamicBuffer.view());

    assertThat(deserializedClientKey).isNotNull();
    assertThat(deserializedClientKey.address()).isNotNull();
  }
}
