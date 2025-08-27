package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.jca.TfhePrivateKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

class PublicKeyTest {

  private PublicKey publicKey;

  @BeforeEach
  void setUp() {
    Config config = new ConfigBuilder().build();
    KeyPair keyPair = config.generateKeys();
    TfhePrivateKey privateKey = (TfhePrivateKey) keyPair.getPrivate();
    ClientKey clientKey = privateKey.clientKey();
    publicKey = clientKey.generatePublicKey();
  }

  @Test
  void serializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = publicKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.pointer()).isNotNull();
    assertThat(dynamicBuffer.length()).isGreaterThan(0);

    PublicKey deserializedPublicKey = PublicKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedPublicKey).isNotNull();
    assertThat(deserializedPublicKey.address()).isNotNull();
  }

  @Test
  void safeSerializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = publicKey.safeSerialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.pointer()).isNotNull();
    assertThat(dynamicBuffer.length()).isGreaterThan(0);

    PublicKey deserializedPublicKey = PublicKey.safeDeserialize(dynamicBuffer.view());

    assertThat(deserializedPublicKey).isNotNull();
    assertThat(deserializedPublicKey.address()).isNotNull();
  }
}
