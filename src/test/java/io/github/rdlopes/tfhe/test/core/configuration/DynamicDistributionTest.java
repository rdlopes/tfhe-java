package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.DynamicDistribution;
import io.github.rdlopes.tfhe.core.configuration.DynamicDistributionPayload;
import io.github.rdlopes.tfhe.core.configuration.Gaussian;
import io.github.rdlopes.tfhe.core.configuration.TUniform;
import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class DynamicDistributionTest {

  @Test
  void definesTUniform() {
    long tag = 42L;
    TUniform tUniform = new TUniform(64);
    DynamicDistributionPayload payload = new DynamicDistributionPayload(tUniform);
    DynamicDistribution distribution = new DynamicDistribution(tag, payload);

    assertThat(distribution).hasFields(tag, payload);

    DynamicDistributionPayload retrievedPayload = distribution.getDistribution();
    TUniform retrievedTUniform = retrievedPayload.getTUniform();
    Gaussian retrievedGaussian = retrievedPayload.getGaussian();

    assertThat(retrievedTUniform).hasBoundLog2(64);
    assertThat(retrievedGaussian).hasStdCloseTo(0.0, offset(1e-10));
  }

  @Test
  void generatesTUniform() {
    DynamicDistribution distribution = DynamicDistribution.tUniform(64);

    DynamicDistributionPayload retrievedPayload = distribution.getDistribution();
    TUniform retrievedTUniform = retrievedPayload.getTUniform();
    Gaussian retrievedGaussian = retrievedPayload.getGaussian();

    assertThat(retrievedTUniform).hasBoundLog2(64);
    assertThat(retrievedGaussian).hasStdCloseTo(0.0, offset(1e-10));
  }

  @Test
  void definesGaussian() {
    long tag = 999L;
    Gaussian gaussian = new Gaussian(5.0);
    DynamicDistributionPayload payload = new DynamicDistributionPayload(gaussian);
    DynamicDistribution distribution = new DynamicDistribution(tag, payload);

    assertThat(distribution).hasFields(tag, payload);

    DynamicDistributionPayload retrievedPayload = distribution.getDistribution();
    Gaussian retrievedGaussian = retrievedPayload.getGaussian();
    TUniform retrievedTUniform = retrievedPayload.getTUniform();

    assertThat(retrievedGaussian).hasStdCloseTo(5.0, offset(1e-10));
    assertThat(retrievedTUniform).hasBoundLog2(0);
  }

  @Test
  void generatesGaussian() {
    DynamicDistribution distribution = DynamicDistribution.gaussian(5.0);

    DynamicDistributionPayload retrievedPayload = distribution.getDistribution();
    Gaussian retrievedGaussian = retrievedPayload.getGaussian();
    TUniform retrievedTUniform = retrievedPayload.getTUniform();

    assertThat(retrievedGaussian).hasStdCloseTo(5.0, offset(1e-10));
    assertThat(retrievedTUniform).hasBoundLog2(0);
  }
}
