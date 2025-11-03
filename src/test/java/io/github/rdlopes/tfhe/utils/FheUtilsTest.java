package io.github.rdlopes.tfhe.utils;

import org.junit.jupiter.api.Test;

import static io.github.rdlopes.tfhe.test.assertions.TfheAssertions.assertThat;
import static io.github.rdlopes.tfhe.utils.FheUtils.nativeType;

class FheUtilsTest {

  @Test
  void producesNativeType() {
    assertThat(nativeType("FheBool"))
      .isEqualTo("fhe_bool");
    assertThat(nativeType("FheInt1024"))
      .isEqualTo("fhe_int1024");
  }

}
