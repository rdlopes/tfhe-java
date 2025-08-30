package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.CompressedCompactPublicKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedCompactPublicKeyTest {

  private ConfigBuilder configBuilder;
  private Config config;
  private ClientKey clientKey;
  private CompressedCompactPublicKey compressedCompactPublicKey;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
    clientKey = config.generateClientKey();
    compressedCompactPublicKey = clientKey.generateCompactPublicKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    compressedCompactPublicKey.destroy();
    config.destroy();
    configBuilder.destroy();
  }

  @Test
  void serializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = compressedCompactPublicKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    CompressedCompactPublicKey deserializedCompressedCompactPublicKey = CompressedCompactPublicKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedCompressedCompactPublicKey).isNotNull();
    assertThat(deserializedCompressedCompactPublicKey.getAddress()).isNotNull();

    deserializedCompressedCompactPublicKey.destroy();
    dynamicBuffer.destroy();
  }

  @Test
  void safeSerializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = compressedCompactPublicKey.safeSerialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    CompressedCompactPublicKey safeDeserializedCompressedCompactPublicKey = CompressedCompactPublicKey.safeDeserialize(dynamicBuffer.view());

    assertThat(safeDeserializedCompressedCompactPublicKey).isNotNull();
    assertThat(safeDeserializedCompressedCompactPublicKey.getAddress()).isNotNull();

    safeDeserializedCompressedCompactPublicKey.destroy();
    dynamicBuffer.destroy();
  }
}
