package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.Config;
import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PublicKeyTest {

  private ConfigBuilder configBuilder;
  private Config config;
  private ClientKey clientKey;
  private PublicKey publicKey;

  @BeforeEach
  void setUp() {
    configBuilder = new ConfigBuilder();
    config = configBuilder.build();
    clientKey = config.generateClientKey();
    publicKey = clientKey.generatePublicKey();
  }

  @AfterEach
  void tearDown() {
    clientKey.destroy();
    publicKey.destroy();
    // crashes the JVM
    // config.destroy();
    // configBuilder.destroy();
  }

  @Test
  @Disabled("serde uses buffers which length exceeds Integer.MAX_VALUE")
  void serializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = publicKey.serialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    PublicKey deserializedPublicKey = PublicKey.deserialize(dynamicBuffer.view());

    assertThat(deserializedPublicKey).isNotNull();
    assertThat(deserializedPublicKey.getAddress()).isNotNull();

    deserializedPublicKey.destroy();
    // crashes the JVM
    // dynamicBuffer.destroy();
  }

  @Test
  @Disabled("serde uses buffers which length exceeds Integer.MAX_VALUE")
  void safeSerializesAndDeserializes() {
    DynamicBuffer dynamicBuffer = publicKey.safeSerialize();

    assertThat(dynamicBuffer).isNotNull();
    assertThat(dynamicBuffer.getPointer()).isNotNull();
    assertThat(dynamicBuffer.getLength()).isGreaterThan(0);

    PublicKey deserializedPublicKey = PublicKey.safeDeserialize(dynamicBuffer.view());

    assertThat(deserializedPublicKey).isNotNull();
    assertThat(deserializedPublicKey.getAddress()).isNotNull();

    deserializedPublicKey.destroy();
    // crashes the JVM
    // dynamicBuffer.destroy();
  }
}
