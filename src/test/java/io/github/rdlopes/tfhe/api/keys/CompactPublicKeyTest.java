package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class CompactPublicKeyTest {
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = KeySet.builder()
                   .build();
  }

  @Test
  void serializesAndDeserializes() {
    CompactPublicKey publicKey = new CompactPublicKey(keySet.getClientKey());

    try (DynamicBuffer buffer = publicKey.serialize()) {
      assertThatCode(() -> CompactPublicKey.deserialize(buffer))
        .doesNotThrowAnyException();
    }
  }

}
