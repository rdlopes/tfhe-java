package io.github.rdlopes.tfhe.api.integers;

import io.github.rdlopes.tfhe.api.types.I512;
import io.github.rdlopes.tfhe.test.assertions.TfheAssertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.api.types.I512.*;
import static java.math.BigInteger.ONE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class I512Test {

  @Test
  public void initializesFromMinimumValue() {
    I512 value = valueOf(MIN_VALUE);
    TfheAssertions.assertThat(value)
                  .hasValue(MIN_VALUE);
  }

  @Test
  public void initializesFromMaximumValue() {
    I512 value = valueOf(MAX_VALUE);
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
