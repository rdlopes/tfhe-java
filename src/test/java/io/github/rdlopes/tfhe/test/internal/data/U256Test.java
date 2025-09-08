package io.github.rdlopes.tfhe.test.internal.data;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.internal.data.U256.*;
import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class U256Test {

  @Test
  void storesMaxValue() {
    assertThat(valueOf(MAX_VALUE)).hasValue(MAX_VALUE);
  }

  @Test
  void storesMinValue() {
    assertThat(valueOf(MIN_VALUE)).hasValue(MIN_VALUE);
  }

  @Test
  void throwsBelowMinimum() {
    BigInteger belowMin = MIN_VALUE.subtract(BigInteger.ONE);
    assertThatThrownBy(() -> valueOf(belowMin)).isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void throwsAboveMaximum() {
    BigInteger aboveMax = MAX_VALUE.add(BigInteger.ONE);
    assertThatThrownBy(() -> valueOf(aboveMax)).isInstanceOf(IllegalArgumentException.class);
  }
}
