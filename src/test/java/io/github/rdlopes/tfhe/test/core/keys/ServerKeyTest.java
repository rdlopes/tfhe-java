package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class ServerKeyTest {

  @Test
  void serializes() {
    ServerKey serverKey = new ConfigBuilder().build()
                                             .generateKeys()
                                             .serverKey();
    DynamicBufferView buffer = serverKey.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);
  }

  @Test
  void doesNotDeserialize() {
    ServerKey serverKey = new ConfigBuilder().build()
                                             .generateKeys()
                                             .serverKey();
    DynamicBufferView buffer = serverKey.serialize();

    assertThatCode(() -> serverKey.deserialize(buffer))
      .isInstanceOf(UnsupportedOperationException.class);
  }
}
