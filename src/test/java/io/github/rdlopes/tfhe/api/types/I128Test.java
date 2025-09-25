package io.github.rdlopes.tfhe.api.types;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.api.types.I128.*;
import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class I128Test {

  @Test
  public void initializesFromMinimumValue() {
    I128 value = valueOf(MIN_VALUE);
    assertThat(value).hasValue(MIN_VALUE);
  }

  @Test
  public void initializesFromMaximumValue() {
    I128 value = valueOf(MAX_VALUE);
    assertThat(value).hasValue(MAX_VALUE);
  }

  @Test
  void throwsIllegalArgumentExceptionBelowMinimumValue() {
    BigInteger belowMin = MIN_VALUE.subtract(BigInteger.ONE);
    assertThatThrownBy(() -> valueOf(belowMin))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void throwsIllegalArgumentExceptionAboveMaximumValue() {
    BigInteger aboveMax = MAX_VALUE.add(BigInteger.ONE);
    assertThatThrownBy(() -> valueOf(aboveMax))
      .isInstanceOf(IllegalArgumentException.class);
  }

}
