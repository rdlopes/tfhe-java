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
    assertThat(parameters.address()).isNotNull();
  }

  @Test
  void customParameters() {
    // Create test values
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

    // Create required complex objects
    DynamicDistribution lweNoise = new DynamicDistribution(1L, new DynamicDistributionPayload(new Gaussian(1.0)));
    DynamicDistribution glweNoise = new DynamicDistribution(1L, new DynamicDistributionPayload(new Gaussian(1.0)));
    ModulusSwitchType modulusSwitch = new ModulusSwitchType(1L, new ModulusSwitchNoiseReductionParams(5, 1.2, 2.1, 0.8));

    // Create parameters using the 16-parameter constructor
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

    // Test all getter methods (using new record method names)
    assertThat(parameters.lweDimension()).isEqualTo(lweDimension);
    assertThat(parameters.glweDimension()).isEqualTo(glweDimension);
    assertThat(parameters.polynomialSize()).isEqualTo(polynomialSize);
    assertThat(parameters.pbsBaseLog()).isEqualTo(pbsBaseLog);
    assertThat(parameters.pbsLevel()).isEqualTo(pbsLevel);
    assertThat(parameters.ksBaseLog()).isEqualTo(ksBaseLog);
    assertThat(parameters.ksLevel()).isEqualTo(ksLevel);
    assertThat(parameters.messageModulus()).isEqualTo(messageModulus);
    assertThat(parameters.carryModulus()).isEqualTo(carryModulus);
    assertThat(parameters.maxNoiseLevel()).isEqualTo(maxNoiseLevel);
    assertThat(parameters.log2pFail()).isEqualTo(log2pFail);
    assertThat(parameters.modulusPowerOf2Exponent()).isEqualTo(modulusPowerOf2Exponent);
    assertThat(parameters.encryptionKeyChoice()).isEqualTo(encryptionKeyChoice);
    assertThat(parameters.lweNoiseDistribution()).isNotNull();
    assertThat(parameters.glweNoiseDistribution()).isNotNull();
    assertThat(parameters.modulusSwitchNoiseReductionParams()).isNotNull();
  }

}
