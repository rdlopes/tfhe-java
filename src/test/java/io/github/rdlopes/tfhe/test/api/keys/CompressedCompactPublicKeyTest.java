package io.github.rdlopes.tfhe.test.api.keys;

import io.github.rdlopes.tfhe.api.config.Config;
import io.github.rdlopes.tfhe.api.config.ConfigBuilder;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.CompressedCompactPublicKey;
import io.github.rdlopes.tfhe.api.keys.KeySet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedCompactPublicKeyTest {
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
    CompressedCompactPublicKey compressedCompactPublicKey = CompressedCompactPublicKey.newWith(clientKey);

    byte[] buffer = compressedCompactPublicKey.serialize();
    CompressedCompactPublicKey deserializedCompressedCompactPublicKey = CompressedCompactPublicKey.deserialize(buffer);

    assertThat(deserializedCompressedCompactPublicKey).isNotNull();
    assertThat(deserializedCompressedCompactPublicKey.getAddress()).isNotNull();
  }
}
