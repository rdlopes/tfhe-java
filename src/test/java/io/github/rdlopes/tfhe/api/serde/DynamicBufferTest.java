package io.github.rdlopes.tfhe.api.serde;

import io.github.rdlopes.tfhe.api.keys.FheKeySet;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DynamicBufferTest {
  private FheKeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = new FheKeySet();
  }

  @Test
  void initializesEmpty() {
    try (DynamicBuffer buffer = new DynamicBuffer()) {
      assertThat(buffer.getLength()).isZero();
    }
  }

  @Test
  void initializesFromEmptyByteArray() {
    byte[] emptyBytes = new byte[0];

    try (DynamicBuffer dynamicBuffer = DynamicBuffer.fromByteArray(emptyBytes)) {
      assertThat(dynamicBuffer.getLength()).isZero();
    }
  }

  @Test
  void initializesFromByteArray() {
    byte[] bytes = {1, 2, 3, 4, 5};

    try (DynamicBuffer dynamicBuffer = DynamicBuffer.fromByteArray(bytes)) {
      assertThat(dynamicBuffer.getLength()).isEqualTo(bytes.length);
    }
  }

  @Test
  @SuppressWarnings("resource")
  void throwsNullPointerExceptionForNullByteArray() {
    assertThatThrownBy(() -> DynamicBuffer.fromByteArray(null))
      .isInstanceOf(NullPointerException.class);
  }

  @Test
  void producesByteArray() {
    try (DynamicBuffer buffer = keySet.getClientKey()
                                      .serialize()) {
      byte[] bytes = buffer.toByteArray();
      assertThat((long) bytes.length).isEqualTo(buffer.getLength());
    }
  }

  @Test
  void throwsWhenByteArrayExceedsLengthLimit() {
    PublicKey publicKey = new PublicKey(keySet.getClientKey());

    try (DynamicBuffer buffer = publicKey.serialize()) {
      assertThatCode(buffer::toByteArray)
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageStartingWith("Buffer length");

    } finally {
      publicKey.destroy();
    }
  }
}
