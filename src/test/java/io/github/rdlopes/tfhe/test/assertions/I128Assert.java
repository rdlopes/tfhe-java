package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.types.I128;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue", "DataFlowIssue"})
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

  public I128Assert isEqualTo(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected I128 to be equal to <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I128Assert isEqualTo(long expected) {
    return isEqualTo(BigInteger.valueOf(expected));
  }

  public I128Assert isEqualTo(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return isEqualTo(new BigInteger(expected));
  }

  public I128Assert isGreaterThan(I128 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I128 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (actualValue.compareTo(expectedValue) <= 0) {
      failWithMessage("Expected I128 to be greater than <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I128Assert isGreaterThan(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (actualValue.compareTo(expected) <= 0) {
      failWithMessage("Expected I128 to be greater than <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I128Assert isGreaterThan(long expected) {
    return isGreaterThan(BigInteger.valueOf(expected));
  }

  public I128Assert isGreaterThan(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return isGreaterThan(new BigInteger(expected));
  }

  public I128Assert hasW0(long expectedW0) {
    isNotNull();
    long actualW0 = actual.getW0();
    if (actualW0 != expectedW0) {
      failWithMessage("Expected I128 w0 to be <%s> but was <%s>", expectedW0, actualW0);
    }
    return this;
  }

  public I128Assert hasW1(long expectedW1) {
    isNotNull();
    long actualW1 = actual.getW1();
    if (actualW1 != expectedW1) {
      failWithMessage("Expected I128 w1 to be <%s> but was <%s>", expectedW1, actualW1);
    }
    return this;
  }

  public I128Assert hasComponents(long expectedW1, long expectedW0) {
    return hasW1(expectedW1).hasW0(expectedW0);
  }
}
