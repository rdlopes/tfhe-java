package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.types.I256;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue", "DataFlowIssue"})
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

  public I256Assert isEqualTo(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected I256 to be equal to <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I256Assert isEqualTo(long expected) {
    return isEqualTo(BigInteger.valueOf(expected));
  }

  public I256Assert isEqualTo(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return isEqualTo(new BigInteger(expected));
  }

  public I256Assert isGreaterThan(I256 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I256 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (actualValue.compareTo(expectedValue) <= 0) {
      failWithMessage("Expected I256 to be greater than <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I256Assert isGreaterThan(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (actualValue.compareTo(expected) <= 0) {
      failWithMessage("Expected I256 to be greater than <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I256Assert isGreaterThan(long expected) {
    return isGreaterThan(BigInteger.valueOf(expected));
  }

  public I256Assert isGreaterThan(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return isGreaterThan(new BigInteger(expected));
  }

  public I256Assert hasW0(long expectedW0) {
    isNotNull();
    long actualW0 = actual.getW0();
    if (actualW0 != expectedW0) {
      failWithMessage("Expected I256 w0 to be <%s> but was <%s>", expectedW0, actualW0);
    }
    return this;
  }

  public I256Assert hasW1(long expectedW1) {
    isNotNull();
    long actualW1 = actual.getW1();
    if (actualW1 != expectedW1) {
      failWithMessage("Expected I256 w1 to be <%s> but was <%s>", expectedW1, actualW1);
    }
    return this;
  }

  public I256Assert hasW2(long expectedW2) {
    isNotNull();
    long actualW2 = actual.getW2();
    if (actualW2 != expectedW2) {
      failWithMessage("Expected I256 w2 to be <%s> but was <%s>", expectedW2, actualW2);
    }
    return this;
  }

  public I256Assert hasW3(long expectedW3) {
    isNotNull();
    long actualW3 = actual.getW3();
    if (actualW3 != expectedW3) {
      failWithMessage("Expected I256 w3 to be <%s> but was <%s>", expectedW3, actualW3);
    }
    return this;
  }

  public I256Assert hasComponents(long expectedW3, long expectedW2, long expectedW1, long expectedW0) {
    return hasW3(expectedW3).hasW2(expectedW2)
                            .hasW1(expectedW1)
                            .hasW0(expectedW0);
  }
}
