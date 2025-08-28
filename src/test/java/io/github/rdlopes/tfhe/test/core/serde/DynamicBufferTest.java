package io.github.rdlopes.tfhe.test.core.serde;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicBufferTest {

  @Test
  void generatesView() {
    Config config = new ConfigBuilder().build();
    KeySet keySet = config.generateKeys();
    ClientKey clientKey = keySet.clientKey();
    DynamicBuffer dynamicBuffer = clientKey.serialize();

    DynamicBufferView view = dynamicBuffer.view();
    assertThat(view).isNotNull();
    assertThat(view.getPointer()).isEqualTo(dynamicBuffer.getPointer());
    assertThat(view.getLength()).isEqualTo(dynamicBuffer.getLength());
  }
}
