package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.CompressedServerKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedServerKeyTest {

  private ConfigBuilder configBuilder;
  private Config config;
  private ClientKey clientKey;
  private CompressedServerKey compressedServerKey;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
    clientKey = config.generateClientKey();
    compressedServerKey = clientKey.generateCompressedServerKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    compressedServerKey.destroy();
    config.destroy();
    configBuilder.destroy();
  }

  @Test
  void serializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = compressedServerKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    CompressedServerKey deserializedCompressedServerKey = CompressedServerKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedCompressedServerKey).isNotNull();
    assertThat(deserializedCompressedServerKey.getAddress()).isNotNull();

    deserializedCompressedServerKey.destroy();
    dynamicBuffer.destroy();
  }

  @Test
  void safeSerializesAndSafeDeserializes() {
    DynamicBuffer dynamicBuffer = compressedServerKey.safeSerialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    CompressedServerKey deserializedCompressedServerKey = CompressedServerKey.safeDeserialize(dynamicBuffer.view());

    assertThat(deserializedCompressedServerKey).isNotNull();
    assertThat(deserializedCompressedServerKey.getAddress()).isNotNull();

    deserializedCompressedServerKey.destroy();
    dynamicBuffer.destroy();
  }

  @Test
  void decompressesToServerKey() {
    ServerKey serverKey = compressedServerKey.decompress();

    assertThat(serverKey).isNotNull();
    assertThat(serverKey.getAddress()).isNotNull();

    serverKey.destroy();
  }
}
