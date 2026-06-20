package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.AfterEach;
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
  
  @AfterEach
  void tearDown() {
    if (keySet != null) keySet.close();
  }
  
  @Test
  void serializesAndDeserializes() {
    try (CompactPublicKey publicKey = new CompactPublicKey(keySet.getClientKey());
         DynamicBuffer buffer = publicKey.serialize()) {
      assertThatCode(() -> CompactPublicKey.deserialize(buffer))
        .doesNotThrowAnyException();
    }
  }

}
