package io.github.rdlopes.tfhe.test.core.keys;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientKeyTest {

  @Test
  void testDefaultConstructor() {
    // Create ClientKey object
    ClientKey clientKey = new ClientKey();

    // Verify object creation and memory segment
    assertThat(clientKey).isNotNull();
    assertThat(clientKey.address()).isNotNull();
  }

  @Test
  void testConstructorWithAddress() {
    // Create ClientKey with existing address
    ClientKey original = new ClientKey();
    ClientKey copy = new ClientKey(original.address());

    // Verify both objects point to same memory
    assertThat(copy).isNotNull();
    assertThat(copy.address()).isEqualTo(original.address());
  }
}
