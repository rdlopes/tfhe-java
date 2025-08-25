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
  void tUniform() {
    long tag = 42L;
    TUniform tUniform = new TUniform(64);
    DynamicDistributionPayload payload = new DynamicDistributionPayload(tUniform);
    DynamicDistribution distribution = new DynamicDistribution(tag, payload);

    assertThat(distribution.tag()).isEqualTo(tag);
    assertThat(distribution.distribution()
                           .tUniform()
                           .boundLog2()).isEqualTo(64);
    assertThat(distribution.distribution()
                           .gaussian()
                           .std()).isCloseTo(0.0, offset(1e-10));
  }

  @Test
  void gaussian() {
    long tag = 999L;
    Gaussian gaussian = new Gaussian(5.0);
    DynamicDistributionPayload payload = new DynamicDistributionPayload(gaussian);
    DynamicDistribution distribution = new DynamicDistribution(tag, payload);

    assertThat(distribution.tag()).isEqualTo(tag);
    assertThat(distribution.distribution()
                           .gaussian()
                           .std()).isEqualTo(5.0);
    assertThat(distribution.distribution()
                           .tUniform()
                           .boundLog2()).isEqualTo(0);
  }

}
