package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
    parameters.cleanNativeResources();
  }

  @Test
  void customParameters() {
    long lweDimension = 512L;
    long glweDimension = 1024L;
    long polynomialSize = 2048L;
    long pbsBaseLog = 6L;
    long pbsLevel = 4L;
    long ksBaseLog = 3L;
    long ksLevel = 7L;
    long messageModulus = 32L;
    long carryModulus = 16L;
    long maxNoiseLevel = 15L;
    double log2pFail = -128.0;
    long modulusPowerOf2Exponent = 64L;
    int encryptionKeyChoice = 2;

    DynamicDistribution lweNoise = new DynamicDistribution(1L, new DynamicDistributionPayload(new Gaussian(1.0)));
    DynamicDistribution glweNoise = new DynamicDistribution(1L, new DynamicDistributionPayload(new Gaussian(1.0)));
    ModulusSwitchType modulusSwitch = new ModulusSwitchType(1L, new ModulusSwitchNoiseReductionParams(5, 1.2, 2.1, 0.8));

    ShortintPBSParameters parameters = new ShortintPBSParameters(
      lweDimension,
      glweDimension,
      polynomialSize,
      lweNoise,
      glweNoise,
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
      modulusSwitch
    );

    assertThat(parameters.getLweDimension()).isEqualTo(lweDimension);
    assertThat(parameters.getGlweDimension()).isEqualTo(glweDimension);
    assertThat(parameters.getPolynomialSize()).isEqualTo(polynomialSize);
    assertThat(parameters.getPbsBaseLog()).isEqualTo(pbsBaseLog);
    assertThat(parameters.getPbsLevel()).isEqualTo(pbsLevel);
    assertThat(parameters.getKsBaseLog()).isEqualTo(ksBaseLog);
    assertThat(parameters.getKsLevel()).isEqualTo(ksLevel);
    assertThat(parameters.getMessageModulus()).isEqualTo(messageModulus);
    assertThat(parameters.getCarryModulus()).isEqualTo(carryModulus);
    assertThat(parameters.getMaxNoiseLevel()).isEqualTo(maxNoiseLevel);
    assertThat(parameters.getLog2pFail()).isEqualTo(log2pFail);
    assertThat(parameters.getModulusPowerOf2Exponent()).isEqualTo(modulusPowerOf2Exponent);
    assertThat(parameters.getEncryptionKeyChoice()).isEqualTo(encryptionKeyChoice);
    assertThat(parameters.getLweNoiseDistribution()).isNotNull();
    assertThat(parameters.getGlweNoiseDistribution()).isNotNull();
    assertThat(parameters.getModulusSwitchNoiseReductionParams()).isNotNull();

    DynamicDistribution retrievedLweNoise = parameters.getLweNoiseDistribution();
    DynamicDistribution retrievedGlweNoise = parameters.getGlweNoiseDistribution();
    ModulusSwitchType retrievedModulusSwitch = parameters.getModulusSwitchNoiseReductionParams();

    retrievedLweNoise.cleanNativeResources();
    retrievedGlweNoise.cleanNativeResources();
    retrievedModulusSwitch.cleanNativeResources();
    parameters.cleanNativeResources();
    modulusSwitch.cleanNativeResources();
    glweNoise.cleanNativeResources();
    lweNoise.cleanNativeResources();
  }

}
