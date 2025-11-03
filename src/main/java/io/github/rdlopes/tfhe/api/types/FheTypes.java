package io.github.rdlopes.tfhe.api.types;

import java.util.EnumSet;

import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public enum FheTypes {

  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt96 = 66,
  /////...
  ///} FheTypes;
  ///```
  FheInt96(Type_FheInt96()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint96 = 41,
  /////...
  ///} FheTypes;
  ///```
  FheUint96(Type_FheUint96()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt112 = 68,
  /////...
  ///} FheTypes;
  ///```
  FheInt112(Type_FheInt112()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint104 = 42,
  /////...
  ///} FheTypes;
  ///```
  FheUint10(Type_FheUint10()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt232 = 81,
  /////...
  ///} FheTypes;
  ///```
  FheInt232(Type_FheInt232()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint152 = 47,
  /////...
  ///} FheTypes;
  ///```
  FheUint152(Type_FheUint152()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt512 = 31,
  /////...
  ///} FheTypes;
  ///```
  FheInt512(Type_FheInt512()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint192 = 51,
  /////...
  ///} FheTypes;
  ///```
  FheUint192(Type_FheUint192()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint168 = 48,
  /////...
  ///} FheTypes;
  ///```
  FheUint16(Type_FheUint16()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint512 = 9,
  /////...
  ///} FheTypes;
  ///```
  FheUint512(Type_FheUint512()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint120 = 44,
  /////...
  ///} FheTypes;
  ///```
  FheUint12(Type_FheUint12()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint232 = 56,
  /////...
  ///} FheTypes;
  ///```
  FheUint232(Type_FheUint232()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint56 = 37,
  /////...
  ///} FheTypes;
  ///```
  FheUint56(Type_FheUint56()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint1024 = 10,
  /////...
  ///} FheTypes;
  ///```
  FheUint1024(Type_FheUint1024()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint144 = 46,
  /////...
  ///} FheTypes;
  ///```
  FheUint14(Type_FheUint14()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint112 = 43,
  /////...
  ///} FheTypes;
  ///```
  FheUint112(Type_FheUint112()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt48 = 61,
  /////...
  ///} FheTypes;
  ///```
  FheInt4(Type_FheInt4()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt248 = 83,
  ///} FheTypes;
  ///```
  FheInt2(Type_FheInt2()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt88 = 65,
  /////...
  ///} FheTypes;
  ///```
  FheInt8(Type_FheInt8()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt64 = 26,
  /////...
  ///} FheTypes;
  ///```
  FheInt6(Type_FheInt6()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt168 = 73,
  /////...
  ///} FheTypes;
  ///```
  FheInt16(Type_FheInt16()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt160 = 28,
  /////...
  ///} FheTypes;
  ///```
  FheInt160(Type_FheInt160()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt144 = 71,
  /////...
  ///} FheTypes;
  ///```
  FheInt14(Type_FheInt14()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt120 = 69,
  /////...
  ///} FheTypes;
  ///```
  FheInt12(Type_FheInt12()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt56 = 62,
  /////...
  ///} FheTypes;
  ///```
  FheInt56(Type_FheInt56()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt120 = 69,
  /////...
  ///} FheTypes;
  ///```
  FheInt120(Type_FheInt120()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt104 = 67,
  /////...
  ///} FheTypes;
  ///```
  FheInt10(Type_FheInt10()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt240 = 82,
  /////...
  ///} FheTypes;
  ///```
  FheInt240(Type_FheInt240()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint184 = 50,
  /////...
  ///} FheTypes;
  ///```
  FheUint184(Type_FheUint184()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt168 = 73,
  /////...
  ///} FheTypes;
  ///```
  FheInt168(Type_FheInt168()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt64 = 26,
  /////...
  ///} FheTypes;
  ///```
  FheInt64(Type_FheInt64()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint40 = 35,
  /////...
  ///} FheTypes;
  ///```
  FheUint40(Type_FheUint40()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheAsciiString = 30,
  /////...
  ///} FheTypes;
  ///```
  FheAsciiString(Type_FheAsciiString()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt200 = 77,
  /////...
  ///} FheTypes;
  ///```
  FheInt200(Type_FheInt200()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt128 = 27,
  /////...
  ///} FheTypes;
  ///```
  FheInt128(Type_FheInt128()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint80 = 39,
  /////...
  ///} FheTypes;
  ///```
  FheUint80(Type_FheUint80()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt248 = 83,
  ///} FheTypes;
  ///```
  FheInt248(Type_FheInt248()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint104 = 42,
  /////...
  ///} FheTypes;
  ///```
  FheUint104(Type_FheUint104()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint224 = 55,
  /////...
  ///} FheTypes;
  ///```
  FheUint224(Type_FheUint224()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint48 = 36,
  /////...
  ///} FheTypes;
  ///```
  FheUint48(Type_FheUint48()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt208 = 78,
  /////...
  ///} FheTypes;
  ///```
  FheInt208(Type_FheInt208()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint144 = 46,
  /////...
  ///} FheTypes;
  ///```
  FheUint144(Type_FheUint144()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint88 = 40,
  /////...
  ///} FheTypes;
  ///```
  FheUint88(Type_FheUint88()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt176 = 74,
  /////...
  ///} FheTypes;
  ///```
  FheInt176(Type_FheInt176()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt248 = 83,
  ///} FheTypes;
  ///```
  FheInt24(Type_FheInt24()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt136 = 70,
  /////...
  ///} FheTypes;
  ///```
  FheInt136(Type_FheInt136()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt256 = 29,
  /////...
  ///} FheTypes;
  ///```
  FheInt256(Type_FheInt256()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt72 = 63,
  /////...
  ///} FheTypes;
  ///```
  FheInt72(Type_FheInt72()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint32 = 4,
  /////...
  ///} FheTypes;
  ///```
  FheUint32(Type_FheUint32()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt216 = 79,
  /////...
  ///} FheTypes;
  ///```
  FheInt216(Type_FheInt216()),
  /// ```c
  /// typedef enum FheTypes {
  ///   Type_FheBool = 0,
  /////...
  ///} FheTypes;
  ///```
  FheBool(Type_FheBool()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint72 = 38,
  /////...
  ///} FheTypes;
  ///```
  FheUint72(Type_FheUint72()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint136 = 45,
  /////...
  ///} FheTypes;
  ///```
  FheUint136(Type_FheUint136()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint216 = 54,
  /////...
  ///} FheTypes;
  ///```
  FheUint216(Type_FheUint216()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint176 = 49,
  /////...
  ///} FheTypes;
  ///```
  FheUint176(Type_FheUint176()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint256 = 8,
  /////...
  ///} FheTypes;
  ///```
  FheUint256(Type_FheUint256()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint88 = 40,
  /////...
  ///} FheTypes;
  ///```
  FheUint8(Type_FheUint8()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint208 = 53,
  /////...
  ///} FheTypes;
  ///```
  FheUint208(Type_FheUint208()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt2048 = 33,
  /////...
  ///} FheTypes;
  ///```
  FheInt2048(Type_FheInt2048()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt32 = 25,
  /////...
  ///} FheTypes;
  ///```
  FheInt32(Type_FheInt32()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt184 = 75,
  /////...
  ///} FheTypes;
  ///```
  FheInt184(Type_FheInt184()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt224 = 80,
  /////...
  ///} FheTypes;
  ///```
  FheInt224(Type_FheInt224()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint120 = 44,
  /////...
  ///} FheTypes;
  ///```
  FheUint120(Type_FheUint120()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint2048 = 11,
  /////...
  ///} FheTypes;
  ///```
  FheUint2048(Type_FheUint2048()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt144 = 71,
  /////...
  ///} FheTypes;
  ///```
  FheInt144(Type_FheInt144()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt40 = 60,
  /////...
  ///} FheTypes;
  ///```
  FheInt40(Type_FheInt40()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint240 = 57,
  /////...
  ///} FheTypes;
  ///```
  FheUint240(Type_FheUint240()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint64 = 5,
  /////...
  ///} FheTypes;
  ///```
  FheUint64(Type_FheUint64()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint160 = 7,
  /////...
  ///} FheTypes;
  ///```
  FheUint160(Type_FheUint160()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt104 = 67,
  /////...
  ///} FheTypes;
  ///```
  FheInt104(Type_FheInt104()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt80 = 64,
  /////...
  ///} FheTypes;
  ///```
  FheInt80(Type_FheInt80()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint48 = 36,
  /////...
  ///} FheTypes;
  ///```
  FheUint4(Type_FheUint4()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint128 = 6,
  /////...
  ///} FheTypes;
  ///```
  FheUint128(Type_FheUint128()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint248 = 58,
  /////...
  ///} FheTypes;
  ///```
  FheUint248(Type_FheUint248()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint6 = 13,
  /////...
  ///} FheTypes;
  ///```
  FheUint6(Type_FheUint6()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint168 = 48,
  /////...
  ///} FheTypes;
  ///```
  FheUint168(Type_FheUint168()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint248 = 58,
  /////...
  ///} FheTypes;
  ///```
  FheUint2(Type_FheUint2()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint200 = 52,
  /////...
  ///} FheTypes;
  ///```
  FheUint200(Type_FheUint200()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheUint248 = 58,
  /////...
  ///} FheTypes;
  ///```
  FheUint24(Type_FheUint24()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt1024 = 32,
  /////...
  ///} FheTypes;
  ///```
  FheInt1024(Type_FheInt1024()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt192 = 76,
  /////...
  ///} FheTypes;
  ///```
  FheInt192(Type_FheInt192()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt48 = 61,
  /////...
  ///} FheTypes;
  ///```
  FheInt48(Type_FheInt48()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt152 = 72,
  /////...
  ///} FheTypes;
  ///```
  FheInt152(Type_FheInt152()),
  /// ```c
  /// typedef enum FheTypes {
  /////...
  ///   Type_FheInt88 = 65,
  /////...
  ///} FheTypes;
  ///```
  FheInt88(Type_FheInt88());

  private final int ordinal;

  FheTypes(int ordinal) {
    this.ordinal = ordinal;
  }

  public int getValue() {
    return ordinal;
  }

  @SuppressWarnings("unused")
  public static FheTypes valueOrdered(int ordinal) {
    return EnumSet.allOf(FheTypes.class)
                  .stream()
                  .filter(e -> e.ordinal == ordinal)
                  .findFirst()
                  .orElseThrow(() -> new IllegalArgumentException("unknown type ordinal"));
  }
}
