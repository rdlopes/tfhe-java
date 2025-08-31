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
    ConfigBuilder configBuilder = new ConfigBuilder();
    ServerKey serverKey = configBuilder.build()
                                       .generateKeys()
                                       .serverKey();
    DynamicBufferView buffer = serverKey.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    buffer.cleanNativeResources();
    serverKey.cleanNativeResources();
    configBuilder.cleanNativeResources();
  }

  @Test
  void doesNotDeserialize() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    ServerKey serverKey = configBuilder.build()
                                       .generateKeys()
                                       .serverKey();
    DynamicBufferView buffer = serverKey.serialize();

    assertThatCode(() -> ServerKey.deserialize(buffer))
      .isInstanceOf(UnsupportedOperationException.class);

    buffer.cleanNativeResources();
    serverKey.cleanNativeResources();
    configBuilder.cleanNativeResources();
  }
}
