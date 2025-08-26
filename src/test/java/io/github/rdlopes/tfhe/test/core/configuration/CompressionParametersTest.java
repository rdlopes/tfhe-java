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

class CompressionParametersTest {

  @SuppressWarnings("unused")
  static List<ArgumentSet> predefinedParameters = Arrays.stream(CompressionParameters.class.getDeclaredFields())
                                                        .filter(field -> field.getType() == CompressionParameters.class)
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
  void predefinedParameters(CompressionParameters parameters) {
    assertThat(parameters).isNotNull();
    assertThat(parameters.address()).isNotNull();
  }

  @Test
  void customParameters() {
    // Create test values
    long brLevel = 4L;
    long brBaseLog = 6L;
    long packingKsLevel = 5L;
    long packingKsBaseLog = 3L;
    long packingKsPolynomialSize = 2048L;
    long packingKsGlweDimension = 1024L;
    long lwePerGlwe = 16L;
    long storageLogModulus = 64L;

    // Create required complex object
    DynamicDistribution packingKsKeyNoiseDistribution = new DynamicDistribution(1L, new DynamicDistributionPayload(new Gaussian(1.0)));

    // Create parameters using the 9-parameter constructor
    CompressionParameters parameters = new CompressionParameters(
      brLevel,
      brBaseLog,
      packingKsLevel,
      packingKsBaseLog,
      packingKsPolynomialSize,
      packingKsGlweDimension,
      lwePerGlwe,
      storageLogModulus,
      packingKsKeyNoiseDistribution
    );

    // Test all getter methods
    assertThat(parameters.brLevel()).isEqualTo(brLevel);
    assertThat(parameters.brBaseLog()).isEqualTo(brBaseLog);
    assertThat(parameters.packingKsLevel()).isEqualTo(packingKsLevel);
    assertThat(parameters.packingKsBaseLog()).isEqualTo(packingKsBaseLog);
    assertThat(parameters.packingKsPolynomialSize()).isEqualTo(packingKsPolynomialSize);
    assertThat(parameters.packingKsGlweDimension()).isEqualTo(packingKsGlweDimension);
    assertThat(parameters.lwePerGlwe()).isEqualTo(lwePerGlwe);
    assertThat(parameters.storageLogModulus()).isEqualTo(storageLogModulus);
    assertThat(parameters.packingKsKeyNoiseDistribution()).isNotNull();
  }

  @Test
  void integrationWithConfigBuilder() {
    // Test that CompressionParameters can be used with ConfigBuilder
    ConfigBuilder configBuilder = new ConfigBuilder();
    CompressionParameters compressionParams = CompressionParameters.SHORTINT_COMP_PARAM_MESSAGE_2_CARRY_2_KS_PBS_TUNIFORM_2M128;

    // This should not throw any exception
    ConfigBuilder result = configBuilder.enableCompression(compressionParams);
    assertThat(result).isNotNull();
    assertThat(result).isSameAs(configBuilder); // Should return the same builder for method chaining
  }

}
