package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import org.junit.jupiter.api.AfterEach;
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
  
  @AfterEach
  void tearDown() {
    if (keySet != null) keySet.close();
  }
  
  @Test
  void serializesAndDeserializes() {
    try (CompressedServerKey compressedKey = new CompressedServerKey(keySet.getClientKey());
         DynamicBuffer buffer = compressedKey.serialize()) {
      assertThatCode(() -> CompressedServerKey.deserialize(buffer))
        .doesNotThrowAnyException();
    }
  }

  @Test
  void decompresses() {
    try (CompressedServerKey compressedKey = new CompressedServerKey(keySet.getClientKey());
         ServerKey decompressed = compressedKey.decompress()) {
      assertThat(decompressed).isNotNull();
    }
  }
}
