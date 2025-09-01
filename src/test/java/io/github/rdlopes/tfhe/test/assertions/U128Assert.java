package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.types.U128;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue", "DataFlowIssue"})
public class U128Assert extends AbstractAssert<U128Assert, U128> {
  public U128Assert(U128 actual) {
    super(actual, U128Assert.class);
  }

  public U128Assert isEqualTo(U128 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected U128 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected U128 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public U128Assert isEqualTo(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    if (expected.signum() < 0) {
      failWithMessage("Expected BigInteger should not be negative for U128 comparison");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected U128 to be equal to <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public U128Assert isEqualTo(long expected) {
    if (expected < 0) {
      failWithMessage("Expected long should not be negative for U128 comparison");
    }
    return isEqualTo(BigInteger.valueOf(expected));
  }

  public U128Assert isEqualTo(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    BigInteger expectedBigInt = new BigInteger(expected);
    if (expectedBigInt.signum() < 0) {
      failWithMessage("Expected String should not represent a negative value for U128 comparison");
    }
    return isEqualTo(expectedBigInt);
  }

  public U128Assert isGreaterThan(U128 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected U128 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (actualValue.compareTo(expectedValue) <= 0) {
      failWithMessage("Expected U128 to be greater than <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public U128Assert isGreaterThan(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    if (expected.signum() < 0) {
      failWithMessage("Expected BigInteger should not be negative for U128 comparison");
    }
    BigInteger actualValue = actual.getValue();
    if (actualValue.compareTo(expected) <= 0) {
      failWithMessage("Expected U128 to be greater than <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public U128Assert isGreaterThan(long expected) {
    if (expected < 0) {
      failWithMessage("Expected long should not be negative for U128 comparison");
    }
    return isGreaterThan(BigInteger.valueOf(expected));
  }

  public U128Assert isGreaterThan(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    BigInteger expectedBigInt = new BigInteger(expected);
    if (expectedBigInt.signum() < 0) {
      failWithMessage("Expected String should not represent a negative value for U128 comparison");
    }
    return isGreaterThan(expectedBigInt);
  }

  public U128Assert hasW0(long expectedW0) {
    isNotNull();
    long actualW0 = actual.getW0();
    if (actualW0 != expectedW0) {
      failWithMessage("Expected U128 w0 to be <%s> but was <%s>", expectedW0, actualW0);
    }
    return this;
  }

  public U128Assert hasW1(long expectedW1) {
    isNotNull();
    long actualW1 = actual.getW1();
    if (actualW1 != expectedW1) {
      failWithMessage("Expected U128 w1 to be <%s> but was <%s>", expectedW1, actualW1);
    }
    return this;
  }

  public U128Assert hasComponents(long expectedW1, long expectedW0) {
    return hasW1(expectedW1).hasW0(expectedW0);
  }
}
