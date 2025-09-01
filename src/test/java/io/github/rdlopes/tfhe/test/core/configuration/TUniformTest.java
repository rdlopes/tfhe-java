package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.TUniform;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;

class TUniformTest {

  private TUniform tUniform;


  @Test
  void zero() {
    tUniform = new TUniform(0);

    assertThat(tUniform).hasBoundLog2(0);
  }

  @Test
  void negative() {
    tUniform = new TUniform(-1);

    assertThat(tUniform).hasBoundLog2(-1);
  }

  @Test
  void positive() {
    tUniform = new TUniform(1);

    assertThat(tUniform).hasBoundLog2(1);
  }

  @Test
  void max() {
    tUniform = new TUniform(Integer.MAX_VALUE);

    assertThat(tUniform).hasBoundLog2(Integer.MAX_VALUE);
  }
}
