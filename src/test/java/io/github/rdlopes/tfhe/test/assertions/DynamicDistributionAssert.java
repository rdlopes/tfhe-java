package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.configuration.DynamicDistribution;
import io.github.rdlopes.tfhe.core.configuration.DynamicDistributionPayload;
import org.assertj.core.api.AbstractAssert;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue"})
public class DynamicDistributionAssert extends AbstractAssert<DynamicDistributionAssert, DynamicDistribution> {

  public DynamicDistributionAssert(DynamicDistribution actual) {
    super(actual, DynamicDistributionAssert.class);
  }

  public static DynamicDistributionAssert assertThat(DynamicDistribution actual) {
    return new DynamicDistributionAssert(actual);
  }

  public DynamicDistributionAssert hasTag(long expected) {
    isNotNull();
    if (actual.getTag() != expected) {
      failWithMessage("Expected tag to be <%d> but was <%d>", expected, actual.getTag());
    }
    return this;
  }

  public DynamicDistributionAssert hasDistribution(DynamicDistributionPayload expected) {
    isNotNull();
    DynamicDistributionPayload actualDistribution = actual.getDistribution();
    if (actualDistribution == null && expected != null) {
      failWithMessage("Expected distribution to be <%s> but was null", expected);
    } else if (actualDistribution != null && expected == null) {
      failWithMessage("Expected distribution to be null but was <%s>", actualDistribution);
    } else if (actualDistribution != null) {
      DynamicDistributionPayloadAssert.assertThat(actualDistribution)
                                      .hasFields(
                                        expected.getTUniform(),
                                        expected.getGaussian()
                                      );
    }
    return this;
  }

  public DynamicDistributionAssert hasFields(long expectedTag, DynamicDistributionPayload expectedDistribution) {
    return hasTag(expectedTag)
      .hasDistribution(expectedDistribution);
  }
}
