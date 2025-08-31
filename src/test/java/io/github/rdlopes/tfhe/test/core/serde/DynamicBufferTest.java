package io.github.rdlopes.tfhe.test.core.serde;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicBufferTest {

  @Test
  void generatesView() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    Config config = configBuilder.build();
    ClientKey clientKey = config.generateClientKey();
    DynamicBufferView view = clientKey.serialize();

    assertThat(view.getPointer()).isNotSameAs(view.getPointer());
    assertThat(view.getLength()).isEqualTo(view.getLength());

    view.cleanNativeResources();
    clientKey.cleanNativeResources();
    config.cleanNativeResources();
    configBuilder.cleanNativeResources();
  }
}
