package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class FheCompressedCompactPublicKeyTest {
  private FheKeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = FheKeySet.builder()
                      .build();
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheCompactPublicKey publicKey = new CompressedFheCompactPublicKey(keySet.getClientKey());

    try (DynamicBuffer buffer = publicKey.serialize()) {
      assertThatCode(() -> CompressedFheCompactPublicKey.deserialize(buffer))
        .doesNotThrowAnyException();
    }
  }

  @Test
  void decompresses() {
    CompressedFheCompactPublicKey publicKey = new CompressedFheCompactPublicKey(keySet.getClientKey());

    CompactPublicKey decompressed = publicKey.decompress();

    assertThat(decompressed).isNotNull();
  }

}
