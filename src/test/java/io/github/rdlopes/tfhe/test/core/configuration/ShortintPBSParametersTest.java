package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.DynamicDistribution;
import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchNoiseReductionParams;
import io.github.rdlopes.tfhe.core.configuration.ModulusSwitchType;
import io.github.rdlopes.tfhe.core.configuration.ShortintPBSParameters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.Arrays;
import java.util.List;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.ArgumentSet;
import static org.junit.jupiter.params.provider.Arguments.argumentSet;

class ShortintPBSParametersTest {

  @SuppressWarnings("unused")
  static List<ArgumentSet> predefinedParameters = Arrays.stream(ShortintPBSParameters.class.getDeclaredFields())
                                                        .filter(field -> field.getType() == ShortintPBSParameters.class)
                                                        .map(field -> {
                                                          try {
                                                            return argumentSet(field.getName(), field.get(null));
                                                          } catch (IllegalAccessException e) {
                                                            throw new RuntimeException(e);
                                                          }
                                                        })
                                                        .toList();

  @ParameterizedTest
  @FieldSource
  void predefinedParameters(ShortintPBSParameters parameters) {
    assertThat(parameters).isNotNull();
    assertThat(parameters.getAddress()).isNotNull();
  }

  @Test
  void customParameters() {
    long lweDimension = 512;
    long glweDimension = 1024;
    long polynomialSize = 2048;
    DynamicDistribution lweNoiseDistribution = DynamicDistribution.gaussian(2.0);
    DynamicDistribution glweNoiseDistribution = DynamicDistribution.tUniform(6);
    long pbsBaseLog = 2;
    long pbsLevel = 3;
    long ksBaseLog = 4;
    long ksLevel = 5;
    long messageModulus = 4;
    long carryModulus = 2;
    long maxNoiseLevel = 15;
    double log2pFail = 3.14;
    long modulusPowerOf2Exponent = 10;
    int encryptionKeyChoice = 0;
    ModulusSwitchType modulusSwitchNoiseReductionParams = new ModulusSwitchType(1, new ModulusSwitchNoiseReductionParams(10, 1.5, 2.0, 0.5));

    ShortintPBSParameters parameters = new ShortintPBSParameters(
      lweDimension,
      glweDimension,
      polynomialSize,
      lweNoiseDistribution,
      glweNoiseDistribution,
      pbsBaseLog,
      pbsLevel,
      ksBaseLog,
      ksLevel,
      messageModulus,
      carryModulus,
      maxNoiseLevel,
      log2pFail,
      modulusPowerOf2Exponent,
      encryptionKeyChoice,
      modulusSwitchNoiseReductionParams
    );

    assertThat(parameters)
      .isNotNull()
      .hasFields(
        lweDimension,
        glweDimension,
        polynomialSize,
        pbsBaseLog,
        pbsLevel,
        ksBaseLog,
        ksLevel,
        messageModulus,
        carryModulus,
        maxNoiseLevel,
        log2pFail,
        modulusPowerOf2Exponent,
        encryptionKeyChoice,
        lweNoiseDistribution,
        glweNoiseDistribution,
        modulusSwitchNoiseReductionParams
      );
  }

}
