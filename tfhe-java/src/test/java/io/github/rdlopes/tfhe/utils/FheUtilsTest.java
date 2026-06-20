package io.github.rdlopes.tfhe.utils;

import io.github.rdlopes.tfhe.api.values.*;
import io.github.rdlopes.tfhe.api.values.extended.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FheUtilsTest {
  
  @Test
  void testUnsignedSignedBoolAndHighBitSize() {
    assertThat(FheUtils.isUnsigned("FheUint8")).isTrue();
    assertThat(FheUtils.isUnsigned("FheInt8")).isFalse();
    
    assertThat(FheUtils.isSigned("FheInt8")).isTrue();
    assertThat(FheUtils.isSigned("FheUint8")).isFalse();
    
    assertThat(FheUtils.isBool("FheBool")).isTrue();
    assertThat(FheUtils.isBool("FheUint8")).isFalse();
    
    assertThat(FheUtils.hasHighBitSize("FheUint128")).isTrue();
    assertThat(FheUtils.hasHighBitSize("FheUint64")).isFalse();
  }
  
  @Test
  void producesNativeType() {
    assertThat(FheUtils.nativeType("FheBool")).isEqualTo("fhe_bool");
    assertThat(FheUtils.nativeType("FheInt1024")).isEqualTo("fhe_int1024");
    assertThat(FheUtils.nativeType("FheUint8")).isEqualTo("fhe_uint8");
  }
  
  @Test
  void testValueClassMapping() {
    assertThat(FheUtils.valueClass("FheBool")).isEqualTo(Boolean.class);
    assertThat(FheUtils.valueClass("FheUint8")).isEqualTo(Byte.class);
    assertThat(FheUtils.valueClass("FheInt16")).isEqualTo(Short.class);
    assertThat(FheUtils.valueClass("FheUint32")).isEqualTo(Integer.class);
    assertThat(FheUtils.valueClass("FheInt64")).isEqualTo(Long.class);
    
    assertThat(FheUtils.valueClass("FheInt128")).isEqualTo(I128.class);
    assertThat(FheUtils.valueClass("FheUint128")).isEqualTo(U128.class);
    
    assertThat(FheUtils.valueClass("FheInt256")).isEqualTo(I256.class);
    assertThat(FheUtils.valueClass("FheUint256")).isEqualTo(U256.class);
    
    assertThat(FheUtils.valueClass("FheInt512")).isEqualTo(I512.class);
    assertThat(FheUtils.valueClass("FheUint512")).isEqualTo(U512.class);
    
    assertThat(FheUtils.valueClass("FheInt1024")).isEqualTo(I1024.class);
    assertThat(FheUtils.valueClass("FheUint1024")).isEqualTo(U1024.class);
    
    assertThat(FheUtils.valueClass("FheInt2048")).isEqualTo(I2048.class);
    assertThat(FheUtils.valueClass("FheUint2048")).isEqualTo(U2048.class);
    
    assertThat(FheUtils.valueClassName("FheUint8")).isEqualTo("Byte");
    
    assertThatThrownBy(() -> FheUtils.valueClass("FheUint9999"))
        .isInstanceOf(IllegalArgumentException.class);
  }
  
  @Test
  void testBitSizeAndLength() {
    assertThat(FheUtils.bitSize("FheBool")).isEqualTo(1);
    assertThat(FheUtils.bitSize("FheUint8")).isEqualTo(8);
    assertThat(FheUtils.bitSize("FheInt16")).isEqualTo(16);
    assertThat(FheUtils.bitSize("FheUint32")).isEqualTo(32);
    assertThat(FheUtils.bitSize("FheInt64")).isEqualTo(64);
    assertThat(FheUtils.bitSize("FheUint128")).isEqualTo(128);
    
    assertThat(FheUtils.bitLength("FheUint10")).isEqualTo(16);
    assertThat(FheUtils.bitLength("FheInt2")).isEqualTo(8);
    
    assertThatThrownBy(() -> FheUtils.bitLength("FheUint9999"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
