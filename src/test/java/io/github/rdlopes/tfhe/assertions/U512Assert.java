package io.github.rdlopes.tfhe.assertions;

import io.github.rdlopes.tfhe.internal.data.U512;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue", "DataFlowIssue"})
public class U512Assert extends AbstractAssert<U512Assert, U512> {
  public U512Assert(U512 actual) {
    super(actual, U512Assert.class);
  }

  public U512Assert isEqualTo(U512 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected U512 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected U512 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public U512Assert hasValue(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    if (expected.signum() < 0) {
      failWithMessage("Expected BigInteger should not be negative for U512 comparison");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected U512 to have value <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public U512Assert hasValue(long expected) {
    if (expected < 0) {
      failWithMessage("Expected long should not be negative for U512 comparison");
    }
    return hasValue(BigInteger.valueOf(expected));
  }

  public U512Assert hasValue(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    BigInteger expectedBigInt = new BigInteger(expected);
    if (expectedBigInt.signum() < 0) {
      failWithMessage("Expected String should not represent a negative value for U512 comparison");
    }
    return hasValue(expectedBigInt);
  }

  public U512Assert isGreaterThan(U512 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected U512 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (actualValue.compareTo(expectedValue) <= 0) {
      failWithMessage("Expected U512 to be greater than <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public U512Assert isGreaterThan(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    if (expected.signum() < 0) {
      failWithMessage("Expected BigInteger should not be negative for U512 comparison");
    }
    BigInteger actualValue = actual.getValue();
    if (actualValue.compareTo(expected) <= 0) {
      failWithMessage("Expected U512 to be greater than <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public U512Assert isGreaterThan(long expected) {
    if (expected < 0) {
      failWithMessage("Expected long should not be negative for U512 comparison");
    }
    return isGreaterThan(BigInteger.valueOf(expected));
  }

  public U512Assert isGreaterThan(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    BigInteger expectedBigInt = new BigInteger(expected);
    if (expectedBigInt.signum() < 0) {
      failWithMessage("Expected String should not represent a negative value for U512 comparison");
    }
    return isGreaterThan(expectedBigInt);
  }

}
