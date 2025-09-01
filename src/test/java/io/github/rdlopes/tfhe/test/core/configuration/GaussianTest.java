package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.Gaussian;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GaussianTest {

  private Gaussian gaussian;


  @Test
  void zero() {
    gaussian = new Gaussian(0.0);

    assertThat(gaussian).isNotNull();
    assertThat(gaussian.getStd()).isEqualTo(0.0);
  }

  @Test
  void negative() {
    gaussian = new Gaussian(-1.5);

    assertThat(gaussian).isNotNull();
    assertThat(gaussian.getStd()).isEqualTo(-1.5);
  }

  @Test
  void positive() {
    gaussian = new Gaussian(1.5);

    assertThat(gaussian).isNotNull();
    assertThat(gaussian.getStd()).isEqualTo(1.5);
  }

  @Test
  void max() {
    gaussian = new Gaussian(Double.MAX_VALUE);

    assertThat(gaussian).isNotNull();
    assertThat(gaussian.getStd()).isEqualTo(Double.MAX_VALUE);
  }
}
