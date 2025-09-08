package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.internal.data.U128;
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

  public U128Assert hasValue(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    if (expected.signum() < 0) {
      failWithMessage("Expected BigInteger should not be negative for U128 comparison");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected U128 to have value <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public U128Assert hasValue(long expected) {
    if (expected < 0) {
      failWithMessage("Expected long should not be negative for U128 comparison");
    }
    return hasValue(BigInteger.valueOf(expected));
  }

  public U128Assert hasValue(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    BigInteger expectedBigInt = new BigInteger(expected);
    if (expectedBigInt.signum() < 0) {
      failWithMessage("Expected String should not represent a negative value for U128 comparison");
    }
    return hasValue(expectedBigInt);
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

}
