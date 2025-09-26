package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class ClientKeyTest {
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = KeySet.builder()
                   .build();
  }

  @Test
  void serializesAndDeserializes() {
    try (DynamicBuffer buffer = keySet.getClientKey()
                                      .serialize()) {
      assertThatCode(() -> ClientKey.deserialize(buffer))
        .doesNotThrowAnyException();
    }
  }

}
