package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientKeyTest {

  @Test
  void serializesAndDeserializes() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    ClientKey clientKey = config.generateClientKey();
    DynamicBufferView buffer = clientKey.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    ClientKey deserialized = ClientKey.deserialize(buffer);

    assertThat(deserialized.getAddress()).isNotNull();

    deserialized.cleanNativeResources();
    buffer.cleanNativeResources();
    clientKey.cleanNativeResources();
    config.cleanNativeResources();
    configBuilder.cleanNativeResources();
  }
}
