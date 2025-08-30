package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PublicKeyTest {

  @Test
  void serializesAndDeserializes() {
    PublicKey publicKey = new ConfigBuilder().build()
                                             .generateClientKey()
                                             .newPublicKey();
    DynamicBufferView buffer = publicKey.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    PublicKey deserialized = new PublicKey();
    deserialized.deserialize(buffer);

    assertThat(deserialized.getAddress()).isNotNull();
  }
}
