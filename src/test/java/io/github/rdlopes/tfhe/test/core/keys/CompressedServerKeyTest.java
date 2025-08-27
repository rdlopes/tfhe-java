package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.CompressedServerKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.jca.TfhePrivateKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedServerKeyTest {

  private ClientKey clientKey;
  private CompressedServerKey compressedServerKey;

  @BeforeEach
  void setUp() {
    Config config = new ConfigBuilder().build();
    KeyPair keyPair = config.generateKeys();
    TfhePrivateKey privateKey = (TfhePrivateKey) keyPair.getPrivate();
    clientKey = privateKey.clientKey();
    compressedServerKey = new CompressedServerKey(clientKey);
  }

  @Test
  void createsFromClientKey() {
    CompressedServerKey newCompressedServerKey = new CompressedServerKey(clientKey);

    assertThat(newCompressedServerKey).isNotNull();
    assertThat(newCompressedServerKey.address()).isNotNull();
  }

  @Test
  void serializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = compressedServerKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.pointer()).isNotNull();
    assertThat(dynamicBuffer.length()).isGreaterThan(0);

    CompressedServerKey deserializedCompressedServerKey = CompressedServerKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedCompressedServerKey).isNotNull();
    assertThat(deserializedCompressedServerKey.address()).isNotNull();
  }

  @Test
  void safeSerializesAndSafeDeserializes() {
    DynamicBuffer dynamicBuffer = compressedServerKey.safeSerialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.pointer()).isNotNull();
    assertThat(dynamicBuffer.length()).isGreaterThan(0);

    CompressedServerKey deserializedCompressedServerKey = CompressedServerKey.safeDeserialize(dynamicBuffer.view());

    assertThat(deserializedCompressedServerKey).isNotNull();
    assertThat(deserializedCompressedServerKey.address()).isNotNull();
  }

  @Test
  void decompressesToServerKey() {
    ServerKey serverKey = compressedServerKey.decompress();

    assertThat(serverKey).isNotNull();
    assertThat(serverKey.address()).isNotNull();
  }

  @Test
  void destroys() {
    CompressedServerKey tempCompressedServerKey = new CompressedServerKey(clientKey);

    tempCompressedServerKey.destroy();
  }
}
