package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.configuration.Gaussian;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.data.Offset;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue"})
public class GaussianAssert extends AbstractAssert<GaussianAssert, Gaussian> {

  public GaussianAssert(Gaussian actual) {
    super(actual, GaussianAssert.class);
  }

  public static GaussianAssert assertThat(Gaussian actual) {
    return new GaussianAssert(actual);
  }

  public GaussianAssert hasStd(double expected) {
    isNotNull();
    if (actual.getStd() != expected) {
      failWithMessage("Expected std to be <%f> but was <%f>", expected, actual.getStd());
    }
    return this;
  }

  public GaussianAssert hasStdCloseTo(double expected, Offset<Double> offset) {
    isNotNull();
    double actualStd = actual.getStd();
    if (Math.abs(actualStd - expected) > offset.value) {
      failWithMessage("Expected std to be close to <%f> within offset <%f> but was <%f>", expected, offset.value, actualStd);
    }
    return this;
  }
}
