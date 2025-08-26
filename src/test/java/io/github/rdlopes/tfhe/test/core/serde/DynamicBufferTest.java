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

class DynamicBufferTest {

  @Test
  void generatesView() {
    Config config = new ConfigBuilder().build();
    KeyPair keyPair = config.generateKeys();
    TfhePrivateKey privateKey = (TfhePrivateKey) keyPair.getPrivate();
    ClientKey clientKey = privateKey.clientKey();
    DynamicBuffer dynamicBuffer = clientKey.serialize();

    DynamicBufferView view = dynamicBuffer.view();
    assertThat(view).isNotNull();
    assertThat(view.pointer()).isEqualTo(dynamicBuffer.pointer());
    assertThat(view.length()).isEqualTo(dynamicBuffer.length());
  }
}
