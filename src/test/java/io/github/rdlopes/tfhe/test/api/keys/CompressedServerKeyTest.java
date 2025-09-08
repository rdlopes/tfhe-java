package io.github.rdlopes.tfhe.test.api.keys;

import io.github.rdlopes.tfhe.api.config.Config;
import io.github.rdlopes.tfhe.api.config.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.CompressedServerKey;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedServerKeyTest {
  private ClientKey clientKey;

  @BeforeEach
  void setUp() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
  }

  @Test
  void serializesAndDeserializes() {
    CompressedServerKey compressedServerKey = CompressedServerKey.newWith(clientKey);

    byte[] buffer = compressedServerKey.serialize();

    assertThat(buffer).isNotNull();
    assertThat(buffer.length).isGreaterThan(0);

    CompressedServerKey deserializedCompressedServerKey = CompressedServerKey.deserialize(buffer);

    assertThat(deserializedCompressedServerKey).isNotNull();
    assertThat(deserializedCompressedServerKey.getAddress()).isNotNull();
  }

  @Test
  void decompressesToServerKey() {
    CompressedServerKey compressedServerKey = CompressedServerKey.newWith(clientKey);

    ServerKey serverKey = compressedServerKey.decompress();

    assertThat(serverKey).isNotNull();
    assertThat(serverKey.getAddress()).isNotNull();
  }
}
