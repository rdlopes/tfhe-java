package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.core.types.I2048;
import org.assertj.core.api.Assertions;

public class TfheAssertions extends Assertions {
  public static I2048Assert assertThat(I2048 actual) {
    return new I2048Assert(actual);
  }
}
