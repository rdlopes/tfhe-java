package io.github.rdlopes.tfhe.test.core.serde;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.KeySet;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DynamicBufferViewTest {

  private ConfigBuilder configBuilder;
  private Config config;
  private ClientKey clientKey;
  private ServerKey serverKey;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
    KeySet keySet = config.generateKeys();
    clientKey = keySet.clientKey();
    serverKey = keySet.serverKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    serverKey.destroy();
    // crashes the JVM
    // config.destroy();
    // configBuilder.destroy();
  }

  @Test
  void serializesToBytes() {
    DynamicBuffer dynamicBuffer = clientKey.serialize();
    DynamicBufferView view = dynamicBuffer.view();
    byte[] bytes = view.toByteArray();

    assertThat(bytes).isNotNull();
    assertThat(bytes.length).isEqualTo(dynamicBuffer.getLength());

    // crashes the JVM
    // dynamicBuffer.destroy();
  }

  @Test
  void deserializesFromBytes() {
    DynamicBuffer dynamicBuffer = clientKey.serialize();
    DynamicBufferView view = dynamicBuffer.view();
    byte[] bytes = view.toByteArray();

    DynamicBufferView view2 = DynamicBufferView.fromByteArray(bytes);

    assertThat(view2).isNotNull();
    assertThat(view2.getLength()).isEqualTo(view.getLength());

    // crashes the JVM
    // dynamicBuffer.destroy();
  }
}
