package io.github.rdlopes.tfhe.test.core.serde;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.jca.TfhePrivateKey;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicBufferViewTest {

  @Test
  void serializesToBytes() {
    Config config = new ConfigBuilder().build();
    KeyPair keyPair = config.generateKeys();
    TfhePrivateKey privateKey = (TfhePrivateKey) keyPair.getPrivate();
    ClientKey clientKey = privateKey.clientKey();

    DynamicBuffer dynamicBuffer = clientKey.serialize();
    DynamicBufferView view = dynamicBuffer.view();
    byte[] bytes = view.toByteArray();

    assertThat(bytes).isNotNull();
    assertThat(bytes.length).isEqualTo(dynamicBuffer.length());
  }

  @Test
  void deserializesFromBytes() {
    Config config = new ConfigBuilder().build();
    KeyPair keyPair = config.generateKeys();
    TfhePrivateKey privateKey = (TfhePrivateKey) keyPair.getPrivate();
    ClientKey clientKey = privateKey.clientKey();
    DynamicBuffer dynamicBuffer = clientKey.serialize();
    DynamicBufferView view = dynamicBuffer.view();
    byte[] bytes = view.toByteArray();
    DynamicBufferView view2 = DynamicBufferView.fromByteArray(bytes);

    assertThat(view2).isNotNull();
    assertThat(view2.length()).isEqualTo(view.length());
  }
}
