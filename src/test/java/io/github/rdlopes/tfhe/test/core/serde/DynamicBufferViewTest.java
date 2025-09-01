package io.github.rdlopes.tfhe.test.core.serde;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicBufferViewTest {

  @Test
  void serializesToBytes() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    ClientKey clientKey = config.generateClientKey();
    DynamicBufferView buffer = clientKey.serialize();
    byte[] bytes = buffer.toByteArray();

    assertThat(bytes.length).isEqualTo(buffer.getLength());

  }

  @Test
  void deserializesFromBytes() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    ClientKey clientKey = config.generateClientKey();
    DynamicBufferView view = clientKey.serialize();
    byte[] bytes = view.toByteArray();
    DynamicBufferView reconstructed = DynamicBufferView.fromByteArray(bytes);

    assertThat(reconstructed.getLength()).isEqualTo(view.getLength());

  }
}
