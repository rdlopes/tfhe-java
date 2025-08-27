package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.CompactPublicKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.jca.TfhePrivateKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

class CompactPublicKeyTest {

  private CompactPublicKey compactPublicKey;

  @BeforeEach
  void setUp() {
    Config config = new ConfigBuilder().build();
    KeyPair keyPair = config.generateKeys();
    TfhePrivateKey privateKey = (TfhePrivateKey) keyPair.getPrivate();
    ClientKey clientKey = privateKey.clientKey();
    compactPublicKey = clientKey.generateCompactPublicKey();
  }

  @Test
  void serializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = compactPublicKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.pointer()).isNotNull();
    assertThat(dynamicBuffer.length()).isGreaterThan(0);

    CompactPublicKey deserializedCompactPublicKey = CompactPublicKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedCompactPublicKey).isNotNull();
    assertThat(deserializedCompactPublicKey.address()).isNotNull();
  }

  @Test
  void safeSerializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = compactPublicKey.safeSerialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.pointer()).isNotNull();
    assertThat(dynamicBuffer.length()).isGreaterThan(0);

    CompactPublicKey safeDeserializedCompactPublicKey = CompactPublicKey.safeDeserialize(dynamicBuffer.view());

    assertThat(safeDeserializedCompactPublicKey).isNotNull();
    assertThat(safeDeserializedCompactPublicKey.address()).isNotNull();
  }

  @Test
  void destroysSuccessfully() {
    assertThat(compactPublicKey.address()).isNotNull();

    compactPublicKey.destroy();
  }
}
