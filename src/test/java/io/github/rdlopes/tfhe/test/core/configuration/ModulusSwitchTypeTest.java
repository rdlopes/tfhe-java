package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchNoiseReductionParams;
import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ModulusSwitchTypeTest {

  @Test
  void customParameters() {
    long tag = 123L;
    ModulusSwitchNoiseReductionParams params = new ModulusSwitchNoiseReductionParams(
      10,
      1.5,
      2.0,
      0.5);

    ModulusSwitchType switchType = new ModulusSwitchType(tag, params);

    assertThat(switchType.tag()).isEqualTo(tag);
    assertThat(switchType.modulusSwitchNoiseReductionParams()).isNotNull();

    ModulusSwitchNoiseReductionParams retrievedParams = switchType.modulusSwitchNoiseReductionParams();
    assertThat(retrievedParams.modulusSwitchZerosCount()).isEqualTo(10);
    assertThat(retrievedParams.msBound()).isEqualTo(1.5);
    assertThat(retrievedParams.msRSigmaFactor()).isEqualTo(2.0);
    assertThat(retrievedParams.msInputVariance()).isEqualTo(0.5);
  }

}
