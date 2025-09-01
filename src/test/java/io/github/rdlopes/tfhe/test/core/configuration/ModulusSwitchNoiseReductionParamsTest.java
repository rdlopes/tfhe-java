package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchNoiseReductionParams;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;

class ModulusSwitchNoiseReductionParamsTest {

  private ModulusSwitchNoiseReductionParams params;

  @Test
  void zeroValues() {
    params = new ModulusSwitchNoiseReductionParams(0, 0.0, 0.0, 0.0);

    assertThat(params).hasFields(0, 0.0, 0.0, 0.0);
  }

  @Test
  void negativeValues() {
    params = new ModulusSwitchNoiseReductionParams(-5, -1.5, -2.3, -0.8);

    assertThat(params).hasFields(-5, -1.5, -2.3, -0.8);
  }

  @Test
  void positiveValues() {
    params = new ModulusSwitchNoiseReductionParams(10, 1.5, 2.0, 0.5);

    assertThat(params).hasFields(10, 1.5, 2.0, 0.5);
  }

  @Test
  void maxValues() {
    params = new ModulusSwitchNoiseReductionParams(Integer.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);

    assertThat(params).hasFields(Integer.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
  }

  @Test
  void minValues() {
    params = new ModulusSwitchNoiseReductionParams(Integer.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);

    assertThat(params).hasFields(Integer.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE, Double.MIN_VALUE);
  }

  @Test
  void customParameters() {
    int modulusSwitchZerosCount = 15;
    double msBound = 3.14;
    double msRSigmaFactor = 2.71;
    double msInputVariance = 1.41;

    params = new ModulusSwitchNoiseReductionParams(
      modulusSwitchZerosCount,
      msBound,
      msRSigmaFactor,
      msInputVariance
    );

    assertThat(params).hasFields(modulusSwitchZerosCount, msBound, msRSigmaFactor, msInputVariance);
  }
}
