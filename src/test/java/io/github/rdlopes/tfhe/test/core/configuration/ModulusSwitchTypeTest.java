package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchNoiseReductionParams;
import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchType;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;

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

    assertThat(switchType).hasFields(tag, params);
  }

}
