package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.Gaussian;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GaussianTest {

  @Test
  void zero() {
    Gaussian gaussian = new Gaussian(0.0);
    assertThat(gaussian).isNotNull();
    assertThat(gaussian.getStd()).isEqualTo(0.0);
  }

  @Test
  void negative() {
    Gaussian gaussian = new Gaussian(-1.5);
    assertThat(gaussian).isNotNull();
    assertThat(gaussian.getStd()).isEqualTo(-1.5);
  }

  @Test
  void positive() {
    Gaussian gaussian = new Gaussian(1.5);
    assertThat(gaussian).isNotNull();
    assertThat(gaussian.getStd()).isEqualTo(1.5);
  }

  @Test
  void max() {
    Gaussian gaussian = new Gaussian(Double.MAX_VALUE);
    assertThat(gaussian).isNotNull();
    assertThat(gaussian.getStd()).isEqualTo(Double.MAX_VALUE);
  }
}
