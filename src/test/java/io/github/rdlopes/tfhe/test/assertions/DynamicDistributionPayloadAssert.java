package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.configuration.DynamicDistributionPayload;
import io.github.rdlopes.tfhe.core.configuration.Gaussian;
import io.github.rdlopes.tfhe.core.configuration.TUniform;
import org.assertj.core.api.AbstractAssert;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue"})
public class DynamicDistributionPayloadAssert extends AbstractAssert<DynamicDistributionPayloadAssert, DynamicDistributionPayload> {

  public DynamicDistributionPayloadAssert(DynamicDistributionPayload actual) {
    super(actual, DynamicDistributionPayloadAssert.class);
  }

  public static DynamicDistributionPayloadAssert assertThat(DynamicDistributionPayload actual) {
    return new DynamicDistributionPayloadAssert(actual);
  }

  public DynamicDistributionPayloadAssert hasTUniform(TUniform expected) {
    isNotNull();
    TUniform actualTUniform = actual.getTUniform();
    if (actualTUniform == null && expected != null) {
      failWithMessage("Expected tUniform to be <%s> but was null", expected);
    } else if (actualTUniform != null && expected == null) {
      failWithMessage("Expected tUniform to be null but was <%s>", actualTUniform);
    } else if (actualTUniform != null) {
      if (actualTUniform.getBoundLog2() != expected.getBoundLog2()) {
        failWithMessage("Expected tUniform boundLog2 to be <%d> but was <%d>", expected.getBoundLog2(), actualTUniform.getBoundLog2());
      }
    }
    return this;
  }

  public DynamicDistributionPayloadAssert hasGaussian(Gaussian expected) {
    isNotNull();
    Gaussian actualGaussian = actual.getGaussian();
    if (actualGaussian == null && expected != null) {
      failWithMessage("Expected gaussian to be <%s> but was null", expected);
    } else if (actualGaussian != null && expected == null) {
      failWithMessage("Expected gaussian to be null but was <%s>", actualGaussian);
    } else if (actualGaussian != null) {
      if (actualGaussian.getStd() != expected.getStd()) {
        failWithMessage("Expected gaussian std to be <%f> but was <%f>", expected.getStd(), actualGaussian.getStd());
      }
    }
    return this;
  }

  public DynamicDistributionPayloadAssert hasFields(TUniform expectedTUniform, Gaussian expectedGaussian) {
    return hasTUniform(expectedTUniform)
      .hasGaussian(expectedGaussian);
  }
}
