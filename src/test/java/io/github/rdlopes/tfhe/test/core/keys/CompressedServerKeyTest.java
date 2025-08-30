package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.CompressedServerKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedServerKeyTest {

  private CompressedServerKey compressedServerKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    ClientKey clientKey = config.generateClientKey();
    compressedServerKey = clientKey.newCompressedServerKey();
  }

  @Test
  void serializesAndDeserializes() {
    DynamicBufferView dynamicBuffer = compressedServerKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    CompressedServerKey deserializedCompressedServerKey = new CompressedServerKey();
    deserializedCompressedServerKey.deserialize(dynamicBuffer);

    assertThat(deserializedCompressedServerKey).isNotNull();
    assertThat(deserializedCompressedServerKey.getAddress()).isNotNull();
  }

  @Test
  void decompressesToServerKey() {
    ServerKey serverKey = compressedServerKey.decompress();

    assertThat(serverKey).isNotNull();
    assertThat(serverKey.getAddress()).isNotNull();
  }
}
