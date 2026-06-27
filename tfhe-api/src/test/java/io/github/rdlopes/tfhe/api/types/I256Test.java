package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.values.extended.I256;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.api.values.extended.I256.*;
import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static java.math.BigInteger.ONE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class I256Test {

  @Test
  void initializesFromMinimumValue() {
    I256 value = of(MIN_VALUE);
    assertThat(value).hasValue(MIN_VALUE);
  }

  @Test
  void initializesFromMaximumValue() {
    I256 value = of(MAX_VALUE);
    assertThat(value).hasValue(MAX_VALUE);
  }

  @Test
  void throwsIllegalArgumentExceptionBelowMinimumValue() {
    BigInteger belowMin = MIN_VALUE.subtract(ONE);
    assertThatThrownBy(() -> of(belowMin))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void throwsIllegalArgumentExceptionAboveMaximumValue() {
    BigInteger aboveMax = MAX_VALUE.add(ONE);
    assertThatThrownBy(() -> of(aboveMax))
      .isInstanceOf(IllegalArgumentException.class);
  }

}
