package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.CompressedServerKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedServerKeyTest {

  @Test
  void serializesAndDeserializes() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    ClientKey clientKey = config.generateClientKey();
    CompressedServerKey compressedServerKey = CompressedServerKey.newWith(clientKey);

    DynamicBufferView dynamicBuffer = compressedServerKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    CompressedServerKey deserializedCompressedServerKey = CompressedServerKey.deserialize(dynamicBuffer);

    assertThat(deserializedCompressedServerKey).isNotNull();
    assertThat(deserializedCompressedServerKey.getAddress()).isNotNull();
  }

  @Test
  void decompressesToServerKey() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    ClientKey clientKey = config.generateClientKey();
    CompressedServerKey compressedServerKey = CompressedServerKey.newWith(clientKey);

    ServerKey serverKey = compressedServerKey.decompress();

    assertThat(serverKey).isNotNull();
    assertThat(serverKey.getAddress()).isNotNull();
  }
}
