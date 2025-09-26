package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class CompressedFheObjectServerKeyTest {
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = KeySet.builder()
                   .build();
  }

  @Test
  void serializesAndDeserializes() {
    CompressedServerKey publicKey = new CompressedServerKey(keySet.getClientKey());

    try (DynamicBuffer buffer = publicKey.serialize()) {
      assertThatCode(() -> CompressedServerKey.deserialize(buffer))
        .doesNotThrowAnyException();
    }
  }

  @Test
  void decompresses() {
    CompressedServerKey publicKey = new CompressedServerKey(keySet.getClientKey());

    ServerKey decompressed = publicKey.decompress();

    assertThat(decompressed).isNotNull();
  }
}
