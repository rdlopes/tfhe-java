package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.api.values.U1024;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"UnusedReturnValue", "DataFlowIssue"})
public class U1024Assert extends AbstractAssert<U1024Assert, U1024> {
  public U1024Assert(U1024 actual) {
    super(actual, U1024Assert.class);
  }

  public U1024Assert isEqualTo(U1024 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected U1024 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected U1024 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public U1024Assert hasValue(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    if (expected.signum() < 0) {
      failWithMessage("Expected BigInteger should not be negative for U1024 comparison");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected U1024 to have value <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public U1024Assert hasValue(long expected) {
    if (expected < 0) {
      failWithMessage("Expected long should not be negative for U1024 comparison");
    }
    return hasValue(BigInteger.valueOf(expected));
  }

  public U1024Assert hasValue(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    BigInteger expectedBigInt = new BigInteger(expected);
    if (expectedBigInt.signum() < 0) {
      failWithMessage("Expected String should not represent a negative value for U1024 comparison");
    }
    return hasValue(expectedBigInt);
  }

}
