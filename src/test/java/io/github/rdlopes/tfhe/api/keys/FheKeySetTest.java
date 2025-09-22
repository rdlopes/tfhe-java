package io.github.rdlopes.tfhe.api.keys;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FheKeySetTest {
  private FheKeySet keySet;

  @BeforeEach
  void setUp() {
    keySet = new FheKeySet();
  }

  @Test
  void generatesKeys() {
    assertThat(keySet.getClientKey()).isNotNull();
    assertThat(keySet.getServerKey()).isNotNull();
  }

}
