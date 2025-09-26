package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class ServerKeyTest {
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = KeySet.builder()
                   .build();
  }

  @Test
  void serializesAndDeserializes() {
    try (DynamicBuffer buffer = keySet.getServerKey()
                                      .serialize()) {
      assertThatCode(() -> ServerKey.deserialize(buffer))
        .doesNotThrowAnyException();
    }
  }

  @Test
  void canBeUsed() {
    assertThatCode(() -> keySet.getServerKey()
                               .use())
      .doesNotThrowAnyException();
  }

  @Test
  void canUnset() {
    keySet.getServerKey()
          .use();
    assertThatCode(() -> keySet.getServerKey()
                               .unset())
      .doesNotThrowAnyException();
  }
}
