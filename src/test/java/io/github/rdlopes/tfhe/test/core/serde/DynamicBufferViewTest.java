package io.github.rdlopes.tfhe.test.core.serde;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicBufferViewTest {

  @Test
  void serializesToBytes() {
    Config config = new ConfigBuilder().build();
    KeySet keySet = config.generateKeys();
    ClientKey clientKey = keySet.clientKey();

    DynamicBuffer dynamicBuffer = clientKey.serialize();
    DynamicBufferView view = dynamicBuffer.view();
    byte[] bytes = view.toByteArray();

    assertThat(bytes).isNotNull();
    assertThat(bytes.length).isEqualTo(dynamicBuffer.getLength());
  }

  @Test
  void deserializesFromBytes() {
    Config config = new ConfigBuilder().build();
    KeySet keySet = config.generateKeys();
    ClientKey clientKey = keySet.clientKey();
    DynamicBuffer dynamicBuffer = clientKey.serialize();
    DynamicBufferView view = dynamicBuffer.view();
    byte[] bytes = view.toByteArray();
    DynamicBufferView view2 = DynamicBufferView.fromByteArray(bytes);

    assertThat(view2).isNotNull();
    assertThat(view2.getLength()).isEqualTo(view.getLength());
  }
}
