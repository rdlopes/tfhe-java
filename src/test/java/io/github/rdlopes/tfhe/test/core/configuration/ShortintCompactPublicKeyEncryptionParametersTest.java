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
    assertThat(parameters.address()).isNotNull();
  }

  @Test
  void customParameters() {
    // Create test values
    long encryptionLweDimension = 512L;
    long messageModulus = 32L;
    long carryModulus = 16L;
    long modulusPowerOf2Exponent = 64L;
    int zkScheme = 1;

    // Create DynamicDistribution (using gaussian for simplicity)
    DynamicDistribution encryptionNoise = new DynamicDistribution(1L, new DynamicDistributionPayload(new Gaussian(1.0)));

    // Create a simplified ShortintPBSParameters with all required parameters
    // Using predefined constant for simplicity instead of creating complex constructor call
    ShortintPBSParameters castingParams = ShortintCompactPublicKeyEncryptionParameters.SHORTINT_PARAM_PKE_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128.castingParameters();

    // Create the parameters using the constructor
    ShortintCompactPublicKeyEncryptionParameters parameters = new ShortintCompactPublicKeyEncryptionParameters(
      encryptionLweDimension,
      encryptionNoise,
      messageModulus,
      carryModulus,
      modulusPowerOf2Exponent,
      castingParams,
      zkScheme
    );

    // Test all getter methods
    assertThat(parameters.encryptionLweDimension()).isEqualTo(encryptionLweDimension);
    assertThat(parameters.messageModulus()).isEqualTo(messageModulus);
    assertThat(parameters.carryModulus()).isEqualTo(carryModulus);
    assertThat(parameters.modulusPowerOf2Exponent()).isEqualTo(modulusPowerOf2Exponent);
    assertThat(parameters.zkScheme()).isEqualTo(zkScheme);
    assertThat(parameters.encryptionNoiseDistribution()).isNotNull();
    assertThat(parameters.castingParameters()).isNotNull();
  }

}
