package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.test.assertions.TfheAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.api.types.I2048.*;
import static java.math.BigInteger.ONE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class I2048Test {

  @Test
  public void initializesFromMinimumValue() {
    I2048 value = valueOf(MIN_VALUE);
    TfheAssertions.assertThat(value)
                  .hasValue(MIN_VALUE);
  }

  @Test
  public void initializesFromMaximumValue() {
    I2048 value = valueOf(MAX_VALUE);
    TfheAssertions.assertThat(value)
                  .hasValue(MAX_VALUE);
  }

  @Test
  void throwsIllegalArgumentExceptionBelowMinimumValue() {
    BigInteger belowMin = MIN_VALUE.subtract(ONE);
    assertThatThrownBy(() -> valueOf(belowMin))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void throwsIllegalArgumentExceptionAboveMaximumValue() {
    BigInteger aboveMax = MAX_VALUE.add(ONE);
    assertThatThrownBy(() -> valueOf(aboveMax))
      .isInstanceOf(IllegalArgumentException.class);
  }
}
