package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.api.types.I1024;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"UnusedReturnValue", "DataFlowIssue"})
public class I1024Assert extends AbstractAssert<I1024Assert, I1024> {
  public I1024Assert(I1024 actual) {
    super(actual, I1024Assert.class);
  }

  public I1024Assert isEqualTo(I1024 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I1024 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected I1024 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I1024Assert hasValue(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected I1024 to have value <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I1024Assert hasValue(long expected) {
    return hasValue(BigInteger.valueOf(expected));
  }

  public I1024Assert hasValue(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return hasValue(new BigInteger(expected));
  }

}
