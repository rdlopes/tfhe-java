package io.github.rdlopes.tfhe.jce;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Basic test class for tfhe-jce module.
 */
public class JceTest {

  @Test
  public void testBasicAssertion() {
    assertThat(true).isTrue();
    assertThat(1 + 1).isEqualTo(2);
  }
}
