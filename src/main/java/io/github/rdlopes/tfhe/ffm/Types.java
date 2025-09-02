package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.MemoryLayout;

import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.*;

@SuppressWarnings("unused")
public enum Types {
  FHE_BOOL(Type_FheBool(), "fhe_bool", "FheBool", 1, 8, true, boolean.class, C_BOOL),
  FHE_UINT2(Type_FheUint2(), "fhe_uint2", "FheUint2", 2, 8, true, byte.class, C_CHAR),
  FHE_UINT4(Type_FheUint4(), "fhe_uint4", "FheUint4", 4, 8, true, byte.class, C_CHAR),
  FHE_UINT6(Type_FheUint6(), "fhe_uint6", "FheUint6", 6, 8, true, byte.class, C_CHAR),
  FHE_UINT8(Type_FheUint8(), "fhe_uint8", "FheUint8", 8, 8, true, byte.class, C_CHAR),
  FHE_UINT10(Type_FheUint10(), "fhe_uint10", "FheUint10", 10, 16, true, short.class, C_SHORT),
  FHE_UINT12(Type_FheUint12(), "fhe_uint12", "FheUint12", 12, 16, true, short.class, C_SHORT),
  FHE_UINT14(Type_FheUint14(), "fhe_uint14", "FheUint14", 14, 16, true, short.class, C_SHORT),
  FHE_UINT16(Type_FheUint16(), "fhe_uint16", "FheUint16", 16, 16, true, short.class, C_SHORT),
  FHE_UINT24(Type_FheUint24(), "fhe_uint24", "FheUint24", 24, 32, true, int.class, C_INT),
  FHE_UINT32(Type_FheUint32(), "fhe_uint32", "FheUint32", 32, 32, true, int.class, C_INT),
  FHE_UINT40(Type_FheUint40(), "fhe_uint40", "FheUint40", 40, 64, true, long.class, C_LONG),
  FHE_UINT48(Type_FheUint48(), "fhe_uint48", "FheUint48", 48, 64, true, long.class, C_LONG),
  FHE_UINT56(Type_FheUint56(), "fhe_uint56", "FheUint56", 56, 64, true, long.class, C_LONG),
  FHE_UINT64(Type_FheUint64(), "fhe_uint64", "FheUint64", 64, 64, true, long.class, C_LONG),
  FHE_UINT72(Type_FheUint72(), "fhe_uint72", "FheUint72", 72, 128, true, U128.class, C_POINTER),
  FHE_UINT80(Type_FheUint80(), "fhe_uint80", "FheUint80", 80, 128, true, U128.class, C_POINTER),
  FHE_UINT88(Type_FheUint88(), "fhe_uint88", "FheUint88", 88, 128, true, U128.class, C_POINTER),
  FHE_UINT96(Type_FheUint96(), "fhe_uint96", "FheUint96", 96, 128, true, U128.class, C_POINTER),
  FHE_UINT104(Type_FheUint104(), "fhe_uint104", "FheUint104", 104, 128, true, U128.class, C_POINTER),
  FHE_UINT112(Type_FheUint112(), "fhe_uint112", "FheUint112", 112, 128, true, U128.class, C_POINTER),
  FHE_UINT120(Type_FheUint120(), "fhe_uint120", "FheUint120", 120, 128, true, U128.class, C_POINTER),
  FHE_UINT128(Type_FheUint128(), "fhe_uint128", "FheUint128", 128, 128, true, U128.class, C_POINTER),
  FHE_UINT136(Type_FheUint136(), "fhe_uint136", "FheUint136", 136, 256, true, U256.class, C_POINTER),
  FHE_UINT144(Type_FheUint144(), "fhe_uint144", "FheUint144", 144, 256, true, U256.class, C_POINTER),
  FHE_UINT152(Type_FheUint152(), "fhe_uint152", "FheUint152", 152, 256, true, U256.class, C_POINTER),
  FHE_UINT160(Type_FheUint160(), "fhe_uint160", "FheUint160", 160, 256, true, U256.class, C_POINTER),
  FHE_UINT168(Type_FheUint168(), "fhe_uint168", "FheUint168", 168, 256, true, U256.class, C_POINTER),
  FHE_UINT176(Type_FheUint176(), "fhe_uint176", "FheUint176", 176, 256, true, U256.class, C_POINTER),
  FHE_UINT184(Type_FheUint184(), "fhe_uint184", "FheUint184", 184, 256, true, U256.class, C_POINTER),
  FHE_UINT192(Type_FheUint192(), "fhe_uint192", "FheUint192", 192, 256, true, U256.class, C_POINTER),
  FHE_UINT200(Type_FheUint200(), "fhe_uint200", "FheUint200", 200, 256, true, U256.class, C_POINTER),
  FHE_UINT208(Type_FheUint208(), "fhe_uint208", "FheUint208", 208, 256, true, U256.class, C_POINTER),
  FHE_UINT216(Type_FheUint216(), "fhe_uint216", "FheUint216", 216, 256, true, U256.class, C_POINTER),
  FHE_UINT224(Type_FheUint224(), "fhe_uint224", "FheUint224", 224, 256, true, U256.class, C_POINTER),
  FHE_UINT232(Type_FheUint232(), "fhe_uint232", "FheUint232", 232, 256, true, U256.class, C_POINTER),
  FHE_UINT240(Type_FheUint240(), "fhe_uint240", "FheUint240", 240, 256, true, U256.class, C_POINTER),
  FHE_UINT248(Type_FheUint248(), "fhe_uint248", "FheUint248", 248, 256, true, U256.class, C_POINTER),
  FHE_UINT256(Type_FheUint256(), "fhe_uint256", "FheUint256", 256, 256, true, U256.class, C_POINTER),
  FHE_UINT512(Type_FheUint512(), "fhe_uint512", "FheUint512", 512, 512, true, U512.class, C_POINTER),
  FHE_UINT1024(Type_FheUint1024(), "fhe_uint1024", "FheUint1024", 1024, 1024, true, U1024.class, C_POINTER),
  FHE_UINT2048(Type_FheUint2048(), "fhe_uint2048", "FheUint2048", 2048, 2048, true, U2048.class, C_POINTER),
  FHE_INT2(Type_FheInt2(), "fhe_int2", "FheInt2", 2, 8, false, byte.class, C_CHAR),
  FHE_INT4(Type_FheInt4(), "fhe_int4", "FheInt4", 4, 8, false, byte.class, C_CHAR),
  FHE_INT6(Type_FheInt6(), "fhe_int6", "FheInt6", 6, 8, false, byte.class, C_CHAR),
  FHE_INT8(Type_FheInt8(), "fhe_int8", "FheInt8", 8, 8, false, byte.class, C_CHAR),
  FHE_INT10(Type_FheInt10(), "fhe_int10", "FheInt10", 10, 16, false, short.class, C_SHORT),
  FHE_INT12(Type_FheInt12(), "fhe_int12", "FheInt12", 12, 16, false, short.class, C_SHORT),
  FHE_INT14(Type_FheInt14(), "fhe_int14", "FheInt14", 14, 16, false, short.class, C_SHORT),
  FHE_INT16(Type_FheInt16(), "fhe_int16", "FheInt16", 16, 16, false, short.class, C_SHORT),
  FHE_INT24(Type_FheInt24(), "fhe_int24", "FheInt24", 24, 32, false, int.class, C_INT),
  FHE_INT32(Type_FheInt32(), "fhe_int32", "FheInt32", 32, 32, false, int.class, C_INT),
  FHE_INT40(Type_FheInt40(), "fhe_int40", "FheInt40", 40, 64, false, long.class, C_LONG),
  FHE_INT48(Type_FheInt48(), "fhe_int48", "FheInt48", 48, 64, false, long.class, C_LONG),
  FHE_INT56(Type_FheInt56(), "fhe_int56", "FheInt56", 56, 64, false, long.class, C_LONG),
  FHE_INT64(Type_FheInt64(), "fhe_int64", "FheInt64", 64, 64, false, long.class, C_LONG),
  FHE_INT72(Type_FheInt72(), "fhe_int72", "FheInt72", 72, 128, false, I128.class, C_POINTER),
  FHE_INT80(Type_FheInt80(), "fhe_int80", "FheInt80", 80, 128, false, I128.class, C_POINTER),
  FHE_INT88(Type_FheInt88(), "fhe_int88", "FheInt88", 88, 128, false, I128.class, C_POINTER),
  FHE_INT96(Type_FheInt96(), "fhe_int96", "FheInt96", 96, 128, false, I128.class, C_POINTER),
  FHE_INT104(Type_FheInt104(), "fhe_int104", "FheInt104", 104, 128, false, I128.class, C_POINTER),
  FHE_INT112(Type_FheInt112(), "fhe_int112", "FheInt112", 112, 128, false, I128.class, C_POINTER),
  FHE_INT120(Type_FheInt120(), "fhe_int120", "FheInt120", 120, 128, false, I128.class, C_POINTER),
  FHE_INT128(Type_FheInt128(), "fhe_int128", "FheInt128", 128, 128, false, I128.class, C_POINTER),
  FHE_INT136(Type_FheInt136(), "fhe_int136", "FheInt136", 136, 256, false, I256.class, C_POINTER),
  FHE_INT144(Type_FheInt144(), "fhe_int144", "FheInt144", 144, 256, false, I256.class, C_POINTER),
  FHE_INT152(Type_FheInt152(), "fhe_int152", "FheInt152", 152, 256, false, I256.class, C_POINTER),
  FHE_INT160(Type_FheInt160(), "fhe_int160", "FheInt160", 160, 256, false, I256.class, C_POINTER),
  FHE_INT168(Type_FheInt168(), "fhe_int168", "FheInt168", 168, 256, false, I256.class, C_POINTER),
  FHE_INT176(Type_FheInt176(), "fhe_int176", "FheInt176", 176, 256, false, I256.class, C_POINTER),
  FHE_INT184(Type_FheInt184(), "fhe_int184", "FheInt184", 184, 256, false, I256.class, C_POINTER),
  FHE_INT192(Type_FheInt192(), "fhe_int192", "FheInt192", 192, 256, false, I256.class, C_POINTER),
  FHE_INT200(Type_FheInt200(), "fhe_int200", "FheInt200", 200, 256, false, I256.class, C_POINTER),
  FHE_INT208(Type_FheInt208(), "fhe_int208", "FheInt208", 208, 256, false, I256.class, C_POINTER),
  FHE_INT216(Type_FheInt216(), "fhe_int216", "FheInt216", 216, 256, false, I256.class, C_POINTER),
  FHE_INT224(Type_FheInt224(), "fhe_int224", "FheInt224", 224, 256, false, I256.class, C_POINTER),
  FHE_INT232(Type_FheInt232(), "fhe_int232", "FheInt232", 232, 256, false, I256.class, C_POINTER),
  FHE_INT240(Type_FheInt240(), "fhe_int240", "FheInt240", 240, 256, false, I256.class, C_POINTER),
  FHE_INT248(Type_FheInt248(), "fhe_int248", "FheInt248", 248, 256, false, I256.class, C_POINTER),
  FHE_INT256(Type_FheInt256(), "fhe_int256", "FheInt256", 256, 256, false, I256.class, C_POINTER),
  FHE_INT512(Type_FheInt512(), "fhe_int512", "FheInt512", 512, 512, false, I1024.class, C_POINTER),
  FHE_INT1024(Type_FheInt1024(), "fhe_int1024", "FheInt1024", 1024, 1024, false, I1024.class, C_POINTER),
  FHE_INT2048(Type_FheInt2048(), "fhe_int2048", "FheInt2048", 2048, 2048, false, I2048.class, C_POINTER);

  private final int fheType;
  private final String nativeName;
  private final String wrappedName;
  private final int bitSize;
  private final int bitLength;
  private final boolean unsigned;
  private final Class<?> valueClass;
  private final MemoryLayout layout;

  Types(int fheType, String nativeName, String wrappedName, int bitSize, int bitLength, boolean unsigned, Class<?> valueClass, MemoryLayout layout) {
    this.fheType = fheType;
    this.nativeName = nativeName;
    this.wrappedName = wrappedName;
    this.bitSize = bitSize;
    this.bitLength = bitLength;
    this.unsigned = unsigned;
    this.valueClass = valueClass;
    this.layout = layout;
  }

  public int bitSize() {
    return bitSize;
  }

  public int bitLength() {
    return bitLength;
  }

  public int fheType() {
    return fheType;
  }

  public String nativeName() {
    return nativeName;
  }

  public String wrappedName() {
    return wrappedName;
  }

  public boolean unsigned() {
    return unsigned;
  }

  public Class<?> valueClass() {
    return valueClass;
  }

  public MemoryLayout layout() {
    return layout;
  }
}
