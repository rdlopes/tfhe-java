package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.api.types.I512;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"UnusedReturnValue", "DataFlowIssue"})
public class I512Assert extends AbstractAssert<I512Assert, I512> {
  public I512Assert(I512 actual) {
    super(actual, I512Assert.class);
  }

  public I512Assert isEqualTo(I512 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I512 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected I512 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I512Assert hasValue(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected I512 to have value <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I512Assert hasValue(long expected) {
    return hasValue(BigInteger.valueOf(expected));
  }

  public I512Assert hasValue(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return hasValue(new BigInteger(expected));
  }

}
