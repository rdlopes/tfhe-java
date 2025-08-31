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

class ShortintCompactPublicKeyEncryptionParametersTest {

  @SuppressWarnings("unused")
  static List<ArgumentSet> predefinedParameters = Arrays.stream(ShortintCompactPublicKeyEncryptionParameters.class.getDeclaredFields())
                                                        .filter(field -> field.getType() == ShortintCompactPublicKeyEncryptionParameters.class)
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
  void predefinedParameters(ShortintCompactPublicKeyEncryptionParameters parameters) {
    assertThat(parameters).isNotNull();
    assertThat(parameters.getAddress()).isNotNull();
    parameters.cleanNativeResources();
  }

  @Test
  void customParameters() {
    long encryptionLweDimension = 512L;
    long messageModulus = 32L;
    long carryModulus = 16L;
    long modulusPowerOf2Exponent = 64L;
    int zkScheme = 1;

    Gaussian gaussian = new Gaussian(1.0);
    DynamicDistributionPayload distribution = new DynamicDistributionPayload(gaussian);
    DynamicDistribution encryptionNoise = new DynamicDistribution(1L, distribution);

    ShortintPBSParameters castingParams = ShortintCompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128.getCastingParameters();

    ShortintCompactPublicKeyEncryptionParameters parameters = new ShortintCompactPublicKeyEncryptionParameters(
      encryptionLweDimension,
      encryptionNoise,
      messageModulus,
      carryModulus,
      modulusPowerOf2Exponent,
      castingParams,
      zkScheme
    );

    assertThat(parameters.getEncryptionLweDimension()).isEqualTo(encryptionLweDimension);
    assertThat(parameters.getMessageModulus()).isEqualTo(messageModulus);
    assertThat(parameters.getCarryModulus()).isEqualTo(carryModulus);
    assertThat(parameters.getModulusPowerOf2Exponent()).isEqualTo(modulusPowerOf2Exponent);
    assertThat(parameters.getZkScheme()).isEqualTo(zkScheme);
    assertThat(parameters.getEncryptionNoiseDistribution()).isNotNull();
    assertThat(parameters.getCastingParameters()).isNotNull();

    distribution.cleanNativeResources();
    gaussian.cleanNativeResources();
    parameters.cleanNativeResources();
    encryptionNoise.cleanNativeResources();
  }

}
