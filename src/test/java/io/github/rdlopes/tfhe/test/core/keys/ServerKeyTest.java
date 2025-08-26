package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.keys.ServerKey;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ServerKeyTest {

  @Test
  void testDefaultConstructor() {
    // Create ServerKey object
    ServerKey serverKey = new ServerKey();

    // Verify object creation and memory segment
    assertThat(serverKey).isNotNull();
    assertThat(serverKey.address()).isNotNull();
  }

  @Test
  void testConstructorWithAddress() {
    // Create ServerKey with existing address
    ServerKey original = new ServerKey();
    ServerKey copy = new ServerKey(original.address());

    // Verify both objects point to same memory
    assertThat(copy).isNotNull();
    assertThat(copy.address()).isEqualTo(original.address());
  }
}
