package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.CompressionParameters;
import io.github.rdlopes.tfhe.core.configuration.DynamicDistribution;
import io.github.rdlopes.tfhe.core.configuration.DynamicDistributionPayload;
import io.github.rdlopes.tfhe.core.configuration.Gaussian;
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
    assertThat(parameters.getAddress()).isNotNull();
  }

  @Test
  void customParameters() {
    long brLevel = 4L;
    long brBaseLog = 6L;
    long packingKsLevel = 5L;
    long packingKsBaseLog = 3L;
    long packingKsPolynomialSize = 2048L;
    long packingKsGlweDimension = 1024L;
    long lwePerGlwe = 16L;
    long storageLogModulus = 64L;

    DynamicDistribution packingKsKeyNoiseDistribution = new DynamicDistribution(1L, new DynamicDistributionPayload(new Gaussian(1.0)));

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

    assertThat(parameters.getBrLevel()).isEqualTo(brLevel);
    assertThat(parameters.getBrBaseLog()).isEqualTo(brBaseLog);
    assertThat(parameters.getPackingKsLevel()).isEqualTo(packingKsLevel);
    assertThat(parameters.getPackingKsBaseLog()).isEqualTo(packingKsBaseLog);
    assertThat(parameters.getPackingKsPolynomialSize()).isEqualTo(packingKsPolynomialSize);
    assertThat(parameters.getPackingKsGlweDimension()).isEqualTo(packingKsGlweDimension);
    assertThat(parameters.getLwePerGlwe()).isEqualTo(lwePerGlwe);
    assertThat(parameters.getStorageLogModulus()).isEqualTo(storageLogModulus);
    assertThat(parameters.getPackingKsKeyNoiseDistribution()).isNotNull();

    parameters.cleanNativeResources();
    packingKsKeyNoiseDistribution.cleanNativeResources();
  }

}
