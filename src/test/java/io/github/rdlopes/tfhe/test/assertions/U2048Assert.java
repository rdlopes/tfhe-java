package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.types.U2048;
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

  public U2048Assert isEqualTo(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    if (expected.signum() < 0) {
      failWithMessage("Expected BigInteger should not be negative for U2048 comparison");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected U2048 to be equal to <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public U2048Assert isEqualTo(long expected) {
    if (expected < 0) {
      failWithMessage("Expected long should not be negative for U2048 comparison");
    }
    return isEqualTo(BigInteger.valueOf(expected));
  }

  public U2048Assert isEqualTo(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    BigInteger expectedBigInt = new BigInteger(expected);
    if (expectedBigInt.signum() < 0) {
      failWithMessage("Expected String should not represent a negative value for U2048 comparison");
    }
    return isEqualTo(expectedBigInt);
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

  public U2048Assert hasWord(int index, long expectedWord) {
    isNotNull();
    long actualWord = actual.getWord(index);
    if (actualWord != expectedWord) {
      failWithMessage("Expected U2048 word[%d] to be <%s> but was <%s>", index, expectedWord, actualWord);
    }
    return this;
  }

  public U2048Assert hasWords(long[] expectedWords) {
    isNotNull();
    if (expectedWords == null) {
      failWithMessage("Expected word array should not be null");
    }
    if (expectedWords.length != 32) {
      failWithMessage("Expected word array should have exactly 32 elements but had <%d>", expectedWords.length);
    }
    long[] actualWords = actual.getWords();
    for (int i = 0; i < 32; i++) {
      if (actualWords[i] != expectedWords[i]) {
        failWithMessage("Expected U2048 word[%d] to be <%s> but was <%s>", i, expectedWords[i], actualWords[i]);
      }
    }
    return this;
  }
}
