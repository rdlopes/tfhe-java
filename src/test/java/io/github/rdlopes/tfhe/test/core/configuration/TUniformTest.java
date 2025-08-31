package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.TUniform;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TUniformTest {

  private TUniform tUniform;

  @AfterEach
  void tearDown() {
    tUniform.cleanNativeResources();
  }

  @Test
  void zero() {
    tUniform = new TUniform(0);

    assertThat(tUniform).isNotNull();
    assertThat(tUniform.getBoundLog2()).isEqualTo(0);
  }

  @Test
  void negative() {
    tUniform = new TUniform(-1);

    assertThat(tUniform).isNotNull();
    assertThat(tUniform.getBoundLog2()).isEqualTo(-1);
  }

  @Test
  void positive() {
    tUniform = new TUniform(1);

    assertThat(tUniform).isNotNull();
    assertThat(tUniform.getBoundLog2()).isEqualTo(1);
  }

  @Test
  void max() {
    tUniform = new TUniform(Integer.MAX_VALUE);

    assertThat(tUniform).isNotNull();
    assertThat(tUniform.getBoundLog2()).isEqualTo(Integer.MAX_VALUE);
  }
}
