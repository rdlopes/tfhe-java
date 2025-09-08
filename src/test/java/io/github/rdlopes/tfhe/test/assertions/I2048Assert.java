package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.internal.data.I2048;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue", "DataFlowIssue"})
public class I2048Assert extends AbstractAssert<I2048Assert, I2048> {
  public I2048Assert(I2048 actual) {
    super(actual, I2048Assert.class);
  }

  public I2048Assert isEqualTo(I2048 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I2048 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected I2048 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I2048Assert hasValue(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected I2048 to have value <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I2048Assert hasValue(long expected) {
    return hasValue(BigInteger.valueOf(expected));
  }

  public I2048Assert hasValue(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return hasValue(new BigInteger(expected));
  }

  public I2048Assert isGreaterThan(I2048 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I2048 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (actualValue.compareTo(expectedValue) <= 0) {
      failWithMessage("Expected I2048 to be greater than <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I2048Assert isGreaterThan(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (actualValue.compareTo(expected) <= 0) {
      failWithMessage("Expected I2048 to be greater than <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I2048Assert isGreaterThan(long expected) {
    return isGreaterThan(BigInteger.valueOf(expected));
  }

  public I2048Assert isGreaterThan(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return isGreaterThan(new BigInteger(expected));
  }

}
