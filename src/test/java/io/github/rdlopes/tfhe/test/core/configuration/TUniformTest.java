package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.TUniform;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TUniformTest {

  @Test
  void zero() {
    TUniform tUniformZero = new TUniform(0);
    assertThat(tUniformZero).isNotNull();
    assertThat(tUniformZero.getBoundLog2()).isEqualTo(0);
  }

  @Test
  void negative() {
    TUniform tUniformNegative = new TUniform(-1);
    assertThat(tUniformNegative).isNotNull();
    assertThat(tUniformNegative.getBoundLog2()).isEqualTo(-1);
  }

  @Test
  void positive() {
    TUniform tUniformPositive = new TUniform(1);
    assertThat(tUniformPositive).isNotNull();
    assertThat(tUniformPositive.getBoundLog2()).isEqualTo(1);
  }

  @Test
  void max() {
    TUniform tUniformMax = new TUniform(Integer.MAX_VALUE);
    assertThat(tUniformMax).isNotNull();
    assertThat(tUniformMax.getBoundLog2()).isEqualTo(Integer.MAX_VALUE);
  }
}
