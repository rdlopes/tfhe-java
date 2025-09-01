package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.types.I512;
import org.assertj.core.api.AbstractAssert;

import java.math.BigInteger;

@SuppressWarnings({"ClassEscapesDefinedScope", "UnusedReturnValue", "DataFlowIssue"})
public class I512Assert extends AbstractAssert<I512Assert, I512> {
  public I512Assert(I512 actual) {
    super(actual, I512Assert.class);
  }

  public I512Assert isEqualTo(I512 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I512 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (!actualValue.equals(expectedValue)) {
      failWithMessage("Expected I512 to be equal to <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I512Assert isEqualTo(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (!actualValue.equals(expected)) {
      failWithMessage("Expected I512 to be equal to <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I512Assert isEqualTo(long expected) {
    return isEqualTo(BigInteger.valueOf(expected));
  }

  public I512Assert isEqualTo(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return isEqualTo(new BigInteger(expected));
  }

  public I512Assert isGreaterThan(I512 expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected I512 should not be null");
    }
    BigInteger actualValue = actual.getValue();
    BigInteger expectedValue = expected.getValue();
    if (actualValue.compareTo(expectedValue) <= 0) {
      failWithMessage("Expected I512 to be greater than <%s> but was <%s>", expectedValue, actualValue);
    }
    return this;
  }

  public I512Assert isGreaterThan(BigInteger expected) {
    isNotNull();
    if (expected == null) {
      failWithMessage("Expected BigInteger should not be null");
    }
    BigInteger actualValue = actual.getValue();
    if (actualValue.compareTo(expected) <= 0) {
      failWithMessage("Expected I512 to be greater than <%s> but was <%s>", expected, actualValue);
    }
    return this;
  }

  public I512Assert isGreaterThan(long expected) {
    return isGreaterThan(BigInteger.valueOf(expected));
  }

  public I512Assert isGreaterThan(String expected) {
    if (expected == null) {
      failWithMessage("Expected String should not be null");
    }
    return isGreaterThan(new BigInteger(expected));
  }

  public I512Assert hasWord(int index, long expectedWord) {
    isNotNull();
    long actualWord = actual.getWord(index);
    if (actualWord != expectedWord) {
      failWithMessage("Expected I512 word[%d] to be <%s> but was <%s>", index, expectedWord, actualWord);
    }
    return this;
  }

  public I512Assert hasWords(long[] expectedWords) {
    isNotNull();
    if (expectedWords == null) {
      failWithMessage("Expected word array should not be null");
    }
    if (expectedWords.length != 8) {
      failWithMessage("Expected word array should have exactly 8 elements but had <%d>", expectedWords.length);
    }
    long[] actualWords = actual.getWords();
    for (int i = 0; i < 8; i++) {
      if (actualWords[i] != expectedWords[i]) {
        failWithMessage("Expected I512 word[%d] to be <%s> but was <%s>", i, expectedWords[i], actualWords[i]);
      }
    }
    return this;
  }
}
