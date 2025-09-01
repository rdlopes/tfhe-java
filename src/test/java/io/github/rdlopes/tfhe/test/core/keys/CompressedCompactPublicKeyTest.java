package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.CompressedCompactPublicKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompressedCompactPublicKeyTest {

  @Test
  void serializesAndDeserializes() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    ClientKey clientKey = config.generateClientKey();
    CompressedCompactPublicKey compressedCompactPublicKey = CompressedCompactPublicKey.newWith(clientKey);

    DynamicBufferView dynamicBuffer = compressedCompactPublicKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    CompressedCompactPublicKey deserializedCompressedCompactPublicKey = CompressedCompactPublicKey.deserialize(dynamicBuffer);

    assertThat(deserializedCompressedCompactPublicKey).isNotNull();
    assertThat(deserializedCompressedCompactPublicKey.getAddress()).isNotNull();
  }
}
