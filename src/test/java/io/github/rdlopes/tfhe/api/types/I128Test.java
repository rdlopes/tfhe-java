package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.values.I128;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.api.values.I128.*;
import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class I128Test {

  @Test
  public void initializesFromMinimumValue() {
    I128 value = of(MIN_VALUE);
    assertThat(value).hasValue(MIN_VALUE);
  }

  @Test
  public void initializesFromMaximumValue() {
    I128 value = of(MAX_VALUE);
    assertThat(value).hasValue(MAX_VALUE);
  }

  @Test
  void throwsIllegalArgumentExceptionBelowMinimumValue() {
    BigInteger belowMin = MIN_VALUE.subtract(BigInteger.ONE);
    assertThatThrownBy(() -> of(belowMin))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void throwsIllegalArgumentExceptionAboveMaximumValue() {
    BigInteger aboveMax = MAX_VALUE.add(BigInteger.ONE);
    assertThatThrownBy(() -> of(aboveMax))
      .isInstanceOf(IllegalArgumentException.class);
  }

}
