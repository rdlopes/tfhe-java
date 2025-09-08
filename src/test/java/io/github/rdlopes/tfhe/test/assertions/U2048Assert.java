package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.internal.data.U2048;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue", "DataFlowIssue"})
public class U2048Assert extends AbstractAssert<U2048Assert, U2048> {
  public U2048Assert(U2048 actual) {
    super(actual, U2048Assert.class);
  }

  public U2048Assert isEqualTo(U2048 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected U2048 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected U2048 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public U2048Assert hasValue(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    if (expected.signum() < 0) {
      failWithMessage("Expected BigInteger should not be negative for U2048 comparison");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected U2048 to have value <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public U2048Assert hasValue(long expected) {
    if (expected < 0) {
      failWithMessage("Expected long should not be negative for U2048 comparison");
    }
    return hasValue(BigInteger.valueOf(expected));
  }

  public U2048Assert hasValue(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    BigInteger expectedBigInt = new BigInteger(expected);
    if (expectedBigInt.signum() < 0) {
      failWithMessage("Expected String should not represent a negative value for U2048 comparison");
    }
    return hasValue(expectedBigInt);
  }

  public U2048Assert isGreaterThan(U2048 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected U2048 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (actualValue.compareTo(expectedValue) <= 0) {
      failWithMessage("Expected U2048 to be greater than <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public U2048Assert isGreaterThan(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    if (expected.signum() < 0) {
      failWithMessage("Expected BigInteger should not be negative for U2048 comparison");
    }
    BigInteger actualValue = actual.getValue();
    if (actualValue.compareTo(expected) <= 0) {
      failWithMessage("Expected U2048 to be greater than <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public U2048Assert isGreaterThan(long expected) {
    if (expected < 0) {
      failWithMessage("Expected long should not be negative for U2048 comparison");
    }
    return isGreaterThan(BigInteger.valueOf(expected));
  }

  public U2048Assert isGreaterThan(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    BigInteger expectedBigInt = new BigInteger(expected);
    if (expectedBigInt.signum() < 0) {
      failWithMessage("Expected String should not represent a negative value for U2048 comparison");
    }
    return isGreaterThan(expectedBigInt);
  }

}
