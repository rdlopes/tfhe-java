package io.github.rdlopes.tfhe.test.core.configuration;

import io.github.rdlopes.tfhe.core.configuration.DynamicDistributionPayload;
import io.github.rdlopes.tfhe.core.configuration.Gaussian;
import io.github.rdlopes.tfhe.core.configuration.TUniform;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

class DynamicDistributionPayloadTest {

  @Test
  void tUniform() {
    TUniform initial = new TUniform(64);
    DynamicDistributionPayload payload = new DynamicDistributionPayload(initial);
    TUniform read = payload.getTUniform();

    assertThat(read.getBoundLog2()).isEqualTo(initial.getBoundLog2());

    Gaussian gaussian = payload.getGaussian();

    assertThat(gaussian.getStd()).isCloseTo(0, offset(1e-10));
  }

  @Test
  void gaussian() {
    Gaussian initial = new Gaussian(5.0);
    DynamicDistributionPayload payload = new DynamicDistributionPayload(initial);
    Gaussian read = payload.getGaussian();

    assertThat(read.getStd()).isEqualTo(initial.getStd());

    TUniform tUniform = payload.getTUniform();

    assertThat(tUniform.getBoundLog2()).isEqualTo(0);
  }

}
