package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

public class CompressedFheObjectServerKeyTest {
  private FheKeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = FheKeySet.builder()
                      .build();
  }

  @Test
  void serializesAndDeserializes() {
    CompressedFheServerKey publicKey = new CompressedFheServerKey(keySet.getClientKey());

    try (DynamicBuffer buffer = publicKey.serialize()) {
      assertThatCode(() -> CompressedFheServerKey.deserialize(buffer))
        .doesNotThrowAnyException();
    }
  }

  @Test
  void decompresses() {
    CompressedFheServerKey publicKey = new CompressedFheServerKey(keySet.getClientKey());

    ServerKey decompressed = publicKey.decompress();

    assertThat(decompressed).isNotNull();
  }
}
