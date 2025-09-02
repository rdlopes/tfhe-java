package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.MemoryLayout;

import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.*;

@SuppressWarnings("unused")
public enum Types {
  FHE_BOOL(Type_FheBool(), 1, 8, true, boolean.class, C_BOOL),
  FHE_UINT2(Type_FheUint2(), 2, 8, true, byte.class, C_CHAR),
  FHE_UINT4(Type_FheUint4(), 4, 8, true, byte.class, C_CHAR),
  FHE_UINT6(Type_FheUint6(), 6, 8, true, byte.class, C_CHAR),
  FHE_UINT8(Type_FheUint8(), 8, 8, true, byte.class, C_CHAR),
  FHE_UINT10(Type_FheUint10(), 10, 16, true, short.class, C_SHORT),
  FHE_UINT12(Type_FheUint12(), 12, 16, true, short.class, C_SHORT),
  FHE_UINT14(Type_FheUint14(), 14, 16, true, short.class, C_SHORT),
  FHE_UINT16(Type_FheUint16(), 16, 16, true, short.class, C_SHORT),
  FHE_UINT24(Type_FheUint24(), 24, 32, true, int.class, C_INT),
  FHE_UINT32(Type_FheUint32(), 32, 32, true, int.class, C_INT),
  FHE_UINT40(Type_FheUint40(), 40, 64, true, long.class, C_LONG),
  FHE_UINT48(Type_FheUint48(), 48, 64, true, long.class, C_LONG),
  FHE_UINT56(Type_FheUint56(), 56, 64, true, long.class, C_LONG),
  FHE_UINT64(Type_FheUint64(), 64, 64, true, long.class, C_LONG),
  FHE_UINT72(Type_FheUint72(), 72, 128, true, U128.class, C_POINTER),
  FHE_UINT80(Type_FheUint80(), 80, 128, true, U128.class, C_POINTER),
  FHE_UINT88(Type_FheUint88(), 88, 128, true, U128.class, C_POINTER),
  FHE_UINT96(Type_FheUint96(), 96, 128, true, U128.class, C_POINTER),
  FHE_UINT104(Type_FheUint104(), 104, 128, true, U128.class, C_POINTER),
  FHE_UINT112(Type_FheUint112(), 112, 128, true, U128.class, C_POINTER),
  FHE_UINT120(Type_FheUint120(), 120, 128, true, U128.class, C_POINTER),
  FHE_UINT128(Type_FheUint128(), 128, 128, true, U128.class, C_POINTER),
  FHE_UINT136(Type_FheUint136(), 136, 256, true, U256.class, C_POINTER),
  FHE_UINT144(Type_FheUint144(), 144, 256, true, U256.class, C_POINTER),
  FHE_UINT152(Type_FheUint152(), 152, 256, true, U256.class, C_POINTER),
  FHE_UINT160(Type_FheUint160(), 160, 256, true, U256.class, C_POINTER),
  FHE_UINT168(Type_FheUint168(), 168, 256, true, U256.class, C_POINTER),
  FHE_UINT176(Type_FheUint176(), 176, 256, true, U256.class, C_POINTER),
  FHE_UINT184(Type_FheUint184(), 184, 256, true, U256.class, C_POINTER),
  FHE_UINT192(Type_FheUint192(), 192, 256, true, U256.class, C_POINTER),
  FHE_UINT200(Type_FheUint200(), 200, 256, true, U256.class, C_POINTER),
  FHE_UINT208(Type_FheUint208(), 208, 256, true, U256.class, C_POINTER),
  FHE_UINT216(Type_FheUint216(), 216, 256, true, U256.class, C_POINTER),
  FHE_UINT224(Type_FheUint224(), 224, 256, true, U256.class, C_POINTER),
  FHE_UINT232(Type_FheUint232(), 232, 256, true, U256.class, C_POINTER),
  FHE_UINT240(Type_FheUint240(), 240, 256, true, U256.class, C_POINTER),
  FHE_UINT248(Type_FheUint248(), 248, 256, true, U256.class, C_POINTER),
  FHE_UINT256(Type_FheUint256(), 256, 256, true, U256.class, C_POINTER),
  FHE_UINT512(Type_FheUint512(), 512, 512, true, U512.class, C_POINTER),
  FHE_UINT1024(Type_FheUint1024(), 1024, 1024, true, U1024.class, C_POINTER),
  FHE_UINT2048(Type_FheUint2048(), 2048, 2048, true, U2048.class, C_POINTER),
  FHE_INT2(Type_FheInt2(), 2, 8, false, byte.class, C_CHAR),
  FHE_INT4(Type_FheInt4(), 4, 8, false, byte.class, C_CHAR),
  FHE_INT6(Type_FheInt6(), 6, 8, false, byte.class, C_CHAR),
  FHE_INT8(Type_FheInt8(), 8, 8, false, byte.class, C_CHAR),
  FHE_INT10(Type_FheInt10(), 10, 16, false, short.class, C_SHORT),
  FHE_INT12(Type_FheInt12(), 12, 16, false, short.class, C_SHORT),
  FHE_INT14(Type_FheInt14(), 14, 16, false, short.class, C_SHORT),
  FHE_INT16(Type_FheInt16(), 16, 16, false, short.class, C_SHORT),
  FHE_INT24(Type_FheInt24(), 24, 32, false, int.class, C_INT),
  FHE_INT32(Type_FheInt32(), 32, 32, false, int.class, C_INT),
  FHE_INT40(Type_FheInt40(), 40, 64, false, long.class, C_LONG),
  FHE_INT48(Type_FheInt48(), 48, 64, false, long.class, C_LONG),
  FHE_INT56(Type_FheInt56(), 56, 64, false, long.class, C_LONG),
  FHE_INT64(Type_FheInt64(), 64, 64, false, long.class, C_LONG),
  FHE_INT72(Type_FheInt72(), 72, 128, false, I128.class, C_POINTER),
  FHE_INT80(Type_FheInt80(), 80, 128, false, I128.class, C_POINTER),
  FHE_INT88(Type_FheInt88(), 88, 128, false, I128.class, C_POINTER),
  FHE_INT96(Type_FheInt96(), 96, 128, false, I128.class, C_POINTER),
  FHE_INT104(Type_FheInt104(), 104, 128, false, I128.class, C_POINTER),
  FHE_INT112(Type_FheInt112(), 112, 128, false, I128.class, C_POINTER),
  FHE_INT120(Type_FheInt120(), 120, 128, false, I128.class, C_POINTER),
  FHE_INT128(Type_FheInt128(), 128, 128, false, I128.class, C_POINTER),
  FHE_INT136(Type_FheInt136(), 136, 256, false, I256.class, C_POINTER),
  FHE_INT144(Type_FheInt144(), 144, 256, false, I256.class, C_POINTER),
  FHE_INT152(Type_FheInt152(), 152, 256, false, I256.class, C_POINTER),
  FHE_INT160(Type_FheInt160(), 160, 256, false, I256.class, C_POINTER),
  FHE_INT168(Type_FheInt168(), 168, 256, false, I256.class, C_POINTER),
  FHE_INT176(Type_FheInt176(), 176, 256, false, I256.class, C_POINTER),
  FHE_INT184(Type_FheInt184(), 184, 256, false, I256.class, C_POINTER),
  FHE_INT192(Type_FheInt192(), 192, 256, false, I256.class, C_POINTER),
  FHE_INT200(Type_FheInt200(), 200, 256, false, I256.class, C_POINTER),
  FHE_INT208(Type_FheInt208(), 208, 256, false, I256.class, C_POINTER),
  FHE_INT216(Type_FheInt216(), 216, 256, false, I256.class, C_POINTER),
  FHE_INT224(Type_FheInt224(), 224, 256, false, I256.class, C_POINTER),
  FHE_INT232(Type_FheInt232(), 232, 256, false, I256.class, C_POINTER),
  FHE_INT240(Type_FheInt240(), 240, 256, false, I256.class, C_POINTER),
  FHE_INT248(Type_FheInt248(), 248, 256, false, I256.class, C_POINTER),
  FHE_INT256(Type_FheInt256(), 256, 256, false, I256.class, C_POINTER),
  FHE_INT512(Type_FheInt512(), 512, 512, false, I1024.class, C_POINTER),
  FHE_INT1024(Type_FheInt1024(), 1024, 1024, false, I1024.class, C_POINTER),
  FHE_INT2048(Type_FheInt2048(), 2048, 2048, false, I2048.class, C_POINTER);

  private final int fheType;
  private final int bitSize;
  private final int bitLength;
  private final boolean unsigned;
  private final Class<?> javaType;
  private final MemoryLayout layout;

  Types(int fheType, int bitSize, int bitLength, boolean unsigned, Class<?> javaType, MemoryLayout layout) {
    this.fheType = fheType;
    this.bitSize = bitSize;
    this.bitLength = bitLength;
    this.unsigned = unsigned;
    this.javaType = javaType;
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

  public boolean unsigned() {
    return unsigned;
  }

  public Class<?> javaType() {
    return javaType;
  }

  public MemoryLayout layout() {
    return layout;
  }
}
