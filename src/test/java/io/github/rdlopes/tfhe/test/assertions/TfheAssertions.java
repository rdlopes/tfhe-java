package io.github.rdlopes.tfhe.test.assertions;

import io.github.rdlopes.tfhe.api.types.*;
import org.assertj.core.api.Assertions;

public class TfheAssertions extends Assertions {
  public static I128Assert assertThat(I128 actual) {
    return new I128Assert(actual);
  }

  public static I256Assert assertThat(I256 actual) {
    return new I256Assert(actual);
  }

  public static I512Assert assertThat(I512 actual) {
    return new I512Assert(actual);
  }

  public static I1024Assert assertThat(I1024 actual) {
    return new I1024Assert(actual);
  }

  public static I2048Assert assertThat(I2048 actual) {
    return new I2048Assert(actual);
  }

  public static U128Assert assertThat(U128 actual) {
    return new U128Assert(actual);
  }

  public static U256Assert assertThat(U256 actual) {
    return new U256Assert(actual);
  }

  public static U512Assert assertThat(U512 actual) {
    return new U512Assert(actual);
  }

  public static U1024Assert assertThat(U1024 actual) {
    return new U1024Assert(actual);
  }

  public static U2048Assert assertThat(U2048 actual) {
    return new U2048Assert(actual);
  }

}
