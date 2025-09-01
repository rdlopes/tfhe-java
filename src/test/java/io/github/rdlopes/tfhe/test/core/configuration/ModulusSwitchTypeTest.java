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

    assertThat(switchType.getTag()).isEqualTo(tag);
    assertThat(switchType.getModulusSwitchNoiseReductionParams()).isNotNull();

    ModulusSwitchNoiseReductionParams retrievedParams = switchType.getModulusSwitchNoiseReductionParams();

    assertThat(retrievedParams.getModulusSwitchZerosCount()).isEqualTo(10);
    assertThat(retrievedParams.getMsBound()).isEqualTo(1.5);
    assertThat(retrievedParams.getMsRSigmaFactor()).isEqualTo(2.0);
    assertThat(retrievedParams.getMsInputVariance()).isEqualTo(0.5);

  }

}
