package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.configuration.TUniform;
import org.assertj.core.api.AbstractAssert;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue"})
public class TUniformAssert extends AbstractAssert<TUniformAssert, TUniform> {

  public TUniformAssert(TUniform actual) {
    super(actual, TUniformAssert.class);
  }

  public static TUniformAssert assertThat(TUniform actual) {
    return new TUniformAssert(actual);
  }

  public TUniformAssert hasBoundLog2(int expected) {
    isNotNull();
    if (actual.getBoundLog2() != expected) {
      failWithMessage("Expected boundLog2 to be <%d> but was <%d>", expected, actual.getBoundLog2());
    }
    return this;
  }
}
