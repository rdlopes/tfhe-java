package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.api.values.I128;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"UnusedReturnValue", "DataFlowIssue"})
public class I128Assert extends AbstractAssert<I128Assert, I128> {
  public I128Assert(I128 actual) {
    super(actual, I128Assert.class);
  }

  public I128Assert isEqualTo(I128 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I128 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected I128 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I128Assert hasValue(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected I128 to have value <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I128Assert hasValue(long expected) {
    return hasValue(BigInteger.valueOf(expected));
  }

  public I128Assert hasValue(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return hasValue(new BigInteger(expected));
  }

}
