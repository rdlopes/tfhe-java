package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.DynamicDistribution;
import io.github.rdlopes.tfhe.core.configuration.DynamicDistributionPayload;
import io.github.rdlopes.tfhe.core.configuration.Gaussian;
import io.github.rdlopes.tfhe.core.configuration.TUniform;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class DynamicDistributionTest {

  @Test
  void definesTUniform() {
    long tag = 42L;
    TUniform tUniform = new TUniform(64);
    DynamicDistributionPayload payload = new DynamicDistributionPayload(tUniform);
    DynamicDistribution distribution = new DynamicDistribution(tag, payload);

    assertThat(distribution.getTag()).isEqualTo(tag);

    DynamicDistributionPayload retrievedPayload = distribution.getDistribution();
    TUniform retrievedTUniform = retrievedPayload.getTUniform();
    Gaussian retrievedGaussian = retrievedPayload.getGaussian();

    assertThat(retrievedTUniform.getBoundLog2()).isEqualTo(64);
    assertThat(retrievedGaussian.getStd()).isCloseTo(0.0, offset(1e-10));

    retrievedTUniform.cleanNativeResources();
    retrievedGaussian.cleanNativeResources();
    retrievedPayload.cleanNativeResources();
    distribution.cleanNativeResources();
    payload.cleanNativeResources();
    tUniform.cleanNativeResources();
  }

  @Test
  void generatesTUniform() {
    DynamicDistribution distribution = DynamicDistribution.tUniform(64);

    DynamicDistributionPayload retrievedPayload = distribution.getDistribution();
    TUniform retrievedTUniform = retrievedPayload.getTUniform();
    Gaussian retrievedGaussian = retrievedPayload.getGaussian();

    assertThat(retrievedTUniform.getBoundLog2()).isEqualTo(64);
    assertThat(retrievedGaussian.getStd()).isCloseTo(0.0, offset(1e-10));

    retrievedTUniform.cleanNativeResources();
    retrievedGaussian.cleanNativeResources();
    retrievedPayload.cleanNativeResources();
    distribution.cleanNativeResources();
  }

  @Test
  void definesGaussian() {
    long tag = 999L;
    Gaussian gaussian = new Gaussian(5.0);
    DynamicDistributionPayload payload = new DynamicDistributionPayload(gaussian);
    DynamicDistribution distribution = new DynamicDistribution(tag, payload);

    assertThat(distribution.getTag()).isEqualTo(tag);

    DynamicDistributionPayload retrievedPayload = distribution.getDistribution();
    Gaussian retrievedGaussian = retrievedPayload.getGaussian();
    TUniform retrievedTUniform = retrievedPayload.getTUniform();

    assertThat(retrievedGaussian.getStd()).isEqualTo(5.0);
    assertThat(retrievedTUniform.getBoundLog2()).isEqualTo(0);

    retrievedGaussian.cleanNativeResources();
    retrievedTUniform.cleanNativeResources();
    retrievedPayload.cleanNativeResources();
    distribution.cleanNativeResources();
    payload.cleanNativeResources();
    gaussian.cleanNativeResources();
  }

  @Test
  void generatesGaussian() {
    DynamicDistribution distribution = DynamicDistribution.gaussian(5.0);

    DynamicDistributionPayload retrievedPayload = distribution.getDistribution();
    Gaussian retrievedGaussian = retrievedPayload.getGaussian();
    TUniform retrievedTUniform = retrievedPayload.getTUniform();

    assertThat(retrievedGaussian.getStd()).isEqualTo(5.0);
    assertThat(retrievedTUniform.getBoundLog2()).isEqualTo(0);

    retrievedGaussian.cleanNativeResources();
    retrievedTUniform.cleanNativeResources();
    retrievedPayload.cleanNativeResources();
    distribution.cleanNativeResources();
  }
}
