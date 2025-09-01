package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.types.I1024;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue", "DataFlowIssue"})
public class I1024Assert extends AbstractAssert<I1024Assert, I1024> {
  public I1024Assert(I1024 actual) {
    super(actual, I1024Assert.class);
  }

  public I1024Assert isEqualTo(I1024 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I1024 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected I1024 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I1024Assert isEqualTo(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected I1024 to be equal to <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I1024Assert isEqualTo(long expected) {
    return isEqualTo(BigInteger.valueOf(expected));
  }

  public I1024Assert isEqualTo(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return isEqualTo(new BigInteger(expected));
  }

  public I1024Assert isGreaterThan(I1024 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I1024 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (actualValue.compareTo(expectedValue) <= 0) {
      failWithMessage("Expected I1024 to be greater than <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I1024Assert isGreaterThan(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (actualValue.compareTo(expected) <= 0) {
      failWithMessage("Expected I1024 to be greater than <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I1024Assert isGreaterThan(long expected) {
    return isGreaterThan(BigInteger.valueOf(expected));
  }

  public I1024Assert isGreaterThan(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return isGreaterThan(new BigInteger(expected));
  }

  public I1024Assert hasWord(int index, long expectedWord) {
    isNotNull();
    long actualWord = actual.getWord(index);
    if (actualWord != expectedWord) {
      failWithMessage("Expected I1024 word[%d] to be <%s> but was <%s>", index, expectedWord, actualWord);
    }
    return this;
  }

  public I1024Assert hasWords(long[] expectedWords) {
    isNotNull();
    if (expectedWords == null) {
      failWithMessage("Expected word array should not be null");
    }
    if (expectedWords.length != 16) {
      failWithMessage("Expected word array should have exactly 16 elements but had <%d>", expectedWords.length);
    }
    long[] actualWords = actual.getWords();
    for (int i = 0; i < 16; i++) {
      if (actualWords[i] != expectedWords[i]) {
        failWithMessage("Expected I1024 word[%d] to be <%s> but was <%s>", i, expectedWords[i], actualWords[i]);
      }
    }
    return this;
  }
}
