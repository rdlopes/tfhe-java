package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.CompressedServerKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedServerKeyTest {

  private ClientKey clientKey;

  @BeforeEach
  void setUp() {
    Config config = new ConfigBuilder().build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
  }

  @Test
  void initializesFromClientKey() {
    CompressedServerKey compressedServerKey = clientKey.generateCompressedPublicKey();

    assertThat(compressedServerKey).isNotNull();
    assertThat(compressedServerKey.getAddress()).isNotNull();
  }

  @Test
  void serializesAndDeserializes() {
    CompressedServerKey compressedServerKey = clientKey.generateCompressedPublicKey();
    DynamicBuffer dynamicBuffer = compressedServerKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    CompressedServerKey deserializedCompressedServerKey = CompressedServerKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedCompressedServerKey).isNotNull();
    assertThat(deserializedCompressedServerKey.getAddress()).isNotNull();
  }

  @Test
  void safeSerializesAndSafeDeserializes() {
    CompressedServerKey compressedServerKey = clientKey.generateCompressedPublicKey();
    DynamicBuffer dynamicBuffer = compressedServerKey.safeSerialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    CompressedServerKey deserializedCompressedServerKey = CompressedServerKey.safeDeserialize(dynamicBuffer.view());

    assertThat(deserializedCompressedServerKey).isNotNull();
    assertThat(deserializedCompressedServerKey.getAddress()).isNotNull();
  }

  @Test
  void decompressesToServerKey() {
    CompressedServerKey compressedServerKey = clientKey.generateCompressedPublicKey();
    ServerKey serverKey = compressedServerKey.decompress();

    assertThat(serverKey).isNotNull();
    assertThat(serverKey.getAddress()).isNotNull();
  }

  @Test
  void destroys() {
    CompressedServerKey compressedServerKey = clientKey.generateCompressedPublicKey();
    compressedServerKey.destroy();
  }
}
