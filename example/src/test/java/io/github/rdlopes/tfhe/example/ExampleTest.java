package io.github.rdlopes.tfhe.example;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic test class for example module.
 */
public class ExampleTest {

  @Test
  public void testBasicAssertion() {
    assertThat(true).isTrue();
    assertThat(1 + 1).isEqualTo(2);
  }
}
