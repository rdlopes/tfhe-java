package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.configuration.ConfigBuilder;
import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.PublicKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PublicKeyTest {

  @Test
  void serializesAndDeserializes() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    ClientKey clientKey = configBuilder.build()
                                       .generateClientKey();
    PublicKey publicKey = PublicKey.newWith(clientKey);
    DynamicBufferView buffer = publicKey.serialize();

    assertThat(buffer.getLength()).isGreaterThan(0);

    PublicKey deserializedPublicKey = PublicKey.deserialize(buffer);

    assertThat(deserializedPublicKey.getAddress()).isNotNull();

    buffer.cleanNativeResources();
    deserializedPublicKey.cleanNativeResources();
    publicKey.cleanNativeResources();
    clientKey.cleanNativeResources();
    configBuilder.cleanNativeResources();
  }
}
