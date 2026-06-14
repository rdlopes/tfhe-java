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

  @Test
  void runWithPropagatesServerKey() {
    ServerKey key = keySet.getServerKey();
    org.assertj.core.api.Assertions.assertThat(ServerKey.current()).isNull();

    key.runWith(() -> {
      org.assertj.core.api.Assertions.assertThat(ServerKey.current()).isSameAs(key);
    });

    org.assertj.core.api.Assertions.assertThat(ServerKey.current()).isNull();
  }

  @Test
  void callWithPropagatesServerKey() throws Exception {
    ServerKey key = keySet.getServerKey();
    org.assertj.core.api.Assertions.assertThat(ServerKey.current()).isNull();

    String result = key.callWith(() -> {
      org.assertj.core.api.Assertions.assertThat(ServerKey.current()).isSameAs(key);
      return "success";
    });

    org.assertj.core.api.Assertions.assertThat(result).isEqualTo("success");
    org.assertj.core.api.Assertions.assertThat(ServerKey.current()).isNull();
  }
}
