package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class FheCompressedCompactPublicKeyTest {
  private KeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = KeySet.builder()
                   .build();
  }

  @Test
  void serializesAndDeserializes() {
    CompressedCompactPublicKey publicKey = new CompressedCompactPublicKey(keySet.getClientKey());

    try (DynamicBuffer buffer = publicKey.serialize()) {
      assertThatCode(() -> CompressedCompactPublicKey.deserialize(buffer))
        .doesNotThrowAnyException();
    }
  }

  @Test
  void decompresses() {
    CompressedCompactPublicKey publicKey = new CompressedCompactPublicKey(keySet.getClientKey());

    CompactPublicKey decompressed = publicKey.decompress();

    assertThat(decompressed).isNotNull();
  }

}
