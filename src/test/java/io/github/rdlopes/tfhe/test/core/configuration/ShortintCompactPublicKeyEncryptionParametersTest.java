package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

import java.util.Arrays;
import java.util.List;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
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
  }

  @Test
  void customParameters() {
    long encryptionLweDimension = 256;
    DynamicDistribution encryptionNoiseDistribution = DynamicDistribution.gaussian(1.5);
    long messageModulus = 4;
    long carryModulus = 2;
    long modulusPowerOf2Exponent = 10;
    ShortintPBSParameters castingParameters = new ShortintPBSParameters(
      512,
      1024,
      2048,
      DynamicDistribution.gaussian(2.0),
      DynamicDistribution.tUniform(6),
      2,
      3,
      4,
      5,
      4,
      2,
      15,
      3.14,
      10,
      0,
      new ModulusSwitchType(1, new ModulusSwitchNoiseReductionParams(10, 1.5, 2.0, 0.5))
    );
    int zkScheme = 1;

    ShortintCompactPublicKeyEncryptionParameters parameters = new ShortintCompactPublicKeyEncryptionParameters(
      encryptionLweDimension,
      encryptionNoiseDistribution,
      messageModulus,
      carryModulus,
      modulusPowerOf2Exponent,
      castingParameters,
      zkScheme
    );

    assertThat(parameters)
      .isNotNull()
      .hasFields(
        encryptionLweDimension,
        messageModulus,
        carryModulus,
        modulusPowerOf2Exponent,
        zkScheme,
        encryptionNoiseDistribution,
        castingParameters
      );
  }

}
