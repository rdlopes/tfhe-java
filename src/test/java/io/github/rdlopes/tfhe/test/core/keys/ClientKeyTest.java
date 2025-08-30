package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientKeyTest {

  @Test
  void serializesAndDeserializes() {
    ClientKey clientKey = new ConfigBuilder().build()
                                             .generateClientKey();
    DynamicBufferView buffer = clientKey.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    ClientKey deserialized = ClientKey.deserialize(buffer);

    assertThat(deserialized.getAddress()).isNotNull();
  }
}
