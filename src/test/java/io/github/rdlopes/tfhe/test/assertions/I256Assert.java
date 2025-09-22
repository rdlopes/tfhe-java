package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.api.types.I256;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"UnusedReturnValue", "DataFlowIssue"})
public class I256Assert extends AbstractAssert<I256Assert, I256> {
  public I256Assert(I256 actual) {
    super(actual, I256Assert.class);
  }

  public I256Assert isEqualTo(I256 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I256 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected I256 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I256Assert hasValue(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected I256 to have value <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I256Assert hasValue(long expected) {
    return hasValue(BigInteger.valueOf(expected));
  }

  public I256Assert hasValue(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return hasValue(new BigInteger(expected));
  }

}
