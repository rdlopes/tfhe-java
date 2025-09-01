package io.github.rdlopes.tfhe.ffm;

import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.*;

public enum TfheTypes {
  FHE_BOOL(Type_FheBool(), 1, 8, false, false),
  FHE_UINT_2(Type_FheUint2(), 2, 8, true, true),
  FHE_UINT_4(Type_FheUint4(), 4, 8, true, true),
  FHE_UINT_6(Type_FheUint6(), 6, 8, true, true),
  FHE_UINT_8(Type_FheUint8(), 8, 8, true, true),
  FHE_UINT_10(Type_FheUint10(), 10, 16, true, true),
  FHE_UINT_12(Type_FheUint12(), 12, 16, true, true),
  FHE_UINT_14(Type_FheUint14(), 14, 16, true, true),
  FHE_UINT_16(Type_FheUint16(), 16, 16, true, true),
  FHE_UINT_24(Type_FheUint24(), 24, 32, true, true),
  FHE_UINT_32(Type_FheUint32(), 32, 32, true, true),
  FHE_UINT_40(Type_FheUint40(), 40, 64, true, true),
  FHE_UINT_48(Type_FheUint48(), 48, 64, true, true),
  FHE_UINT_56(Type_FheUint56(), 56, 64, true, true),
  FHE_UINT_64(Type_FheUint64(), 64, 64, true, true),
  FHE_UINT_72(Type_FheUint72(), 72, 128, true, true),
  FHE_UINT_80(Type_FheUint80(), 80, 128, true, true),
  FHE_UINT_88(Type_FheUint88(), 88, 128, true, true),
  FHE_UINT_96(Type_FheUint96(), 96, 128, true, true),
  FHE_UINT_104(Type_FheUint104(), 104, 128, true, true),
  FHE_UINT_112(Type_FheUint112(), 112, 128, true, true),
  FHE_UINT_120(Type_FheUint120(), 120, 128, true, true),
  FHE_UINT_128(Type_FheUint128(), 128, 128, true, true),
  FHE_UINT_136(Type_FheUint136(), 136, 256, true, true),
  FHE_UINT_144(Type_FheUint144(), 144, 256, true, true),
  FHE_UINT_152(Type_FheUint152(), 152, 256, true, true),
  FHE_UINT_160(Type_FheUint160(), 160, 256, true, true),
  FHE_UINT_168(Type_FheUint168(), 168, 256, true, true),
  FHE_UINT_176(Type_FheUint176(), 176, 256, true, true),
  FHE_UINT_184(Type_FheUint184(), 184, 256, true, true),
  FHE_UINT_192(Type_FheUint192(), 192, 256, true, true),
  FHE_UINT_200(Type_FheUint200(), 200, 256, true, true),
  FHE_UINT_208(Type_FheUint208(), 208, 256, true, true),
  FHE_UINT_216(Type_FheUint216(), 216, 256, true, true),
  FHE_UINT_224(Type_FheUint224(), 224, 256, true, true),
  FHE_UINT_232(Type_FheUint232(), 232, 256, true, true),
  FHE_UINT_240(Type_FheUint240(), 240, 256, true, true),
  FHE_UINT_248(Type_FheUint248(), 248, 256, true, true),
  FHE_UINT_256(Type_FheUint256(), 256, 256, true, true),
  FHE_UINT_512(Type_FheUint512(), 512, 512, true, true),
  FHE_UINT_1024(Type_FheUint1024(), 1024, 1024, true, true),
  FHE_UINT_2048(Type_FheUint2048(), 2048, 2048, true, true),
  FHE_INT_2(Type_FheInt2(), 2, 8, true, false),
  FHE_INT_4(Type_FheInt4(), 4, 8, true, false),
  FHE_INT_6(Type_FheInt6(), 6, 8, true, false),
  FHE_INT_8(Type_FheInt8(), 8, 8, true, false),
  FHE_INT_10(Type_FheInt10(), 10, 16, true, false),
  FHE_INT_12(Type_FheInt12(), 12, 16, true, false),
  FHE_INT_14(Type_FheInt14(), 14, 16, true, false),
  FHE_INT_16(Type_FheInt16(), 16, 16, true, false),
  FHE_INT_24(Type_FheInt24(), 24, 32, true, false),
  FHE_INT_32(Type_FheInt32(), 32, 32, true, false),
  FHE_INT_40(Type_FheInt40(), 40, 64, true, false),
  FHE_INT_48(Type_FheInt48(), 48, 64, true, false),
  FHE_INT_56(Type_FheInt56(), 56, 64, true, false),
  FHE_INT_64(Type_FheInt64(), 64, 64, true, false),
  FHE_INT_72(Type_FheInt72(), 72, 128, true, false),
  FHE_INT_80(Type_FheInt80(), 80, 128, true, false),
  FHE_INT_88(Type_FheInt88(), 88, 128, true, false),
  FHE_INT_96(Type_FheInt96(), 96, 128, true, false),
  FHE_INT_104(Type_FheInt104(), 104, 128, true, false),
  FHE_INT_112(Type_FheInt112(), 112, 128, true, false),
  FHE_INT_120(Type_FheInt120(), 120, 128, true, false),
  FHE_INT_128(Type_FheInt128(), 128, 128, true, false),
  FHE_INT_136(Type_FheInt136(), 136, 256, true, false),
  FHE_INT_144(Type_FheInt144(), 144, 256, true, false),
  FHE_INT_152(Type_FheInt152(), 152, 256, true, false),
  FHE_INT_160(Type_FheInt160(), 160, 256, true, false),
  FHE_INT_168(Type_FheInt168(), 168, 256, true, false),
  FHE_INT_176(Type_FheInt176(), 176, 256, true, false),
  FHE_INT_184(Type_FheInt184(), 184, 256, true, false),
  FHE_INT_192(Type_FheInt192(), 192, 256, true, false),
  FHE_INT_200(Type_FheInt200(), 200, 256, true, false),
  FHE_INT_208(Type_FheInt208(), 208, 256, true, false),
  FHE_INT_216(Type_FheInt216(), 216, 256, true, false),
  FHE_INT_224(Type_FheInt224(), 224, 256, true, false),
  FHE_INT_232(Type_FheInt232(), 232, 256, true, false),
  FHE_INT_240(Type_FheInt240(), 240, 256, true, false),
  FHE_INT_248(Type_FheInt248(), 248, 256, true, false),
  FHE_INT_256(Type_FheInt256(), 256, 256, true, false),
  FHE_INT_512(Type_FheInt512(), 512, 512, true, false),
  FHE_INT_1024(Type_FheInt1024(), 1024, 1024, true, false),
  FHE_INT_2048(Type_FheInt2048(), 2048, 2048, true, false),
  FHE_ASCII_STRING(Type_FheAsciiString(), 8, 8, false, false);

  private final int fheType;
  private final int bitSize;
  private final int bitLength;
  private final boolean integer;
  private final boolean unsigned;

  TfheTypes(int fheType, int bitSize, int bitLength, boolean integer, boolean unsigned) {
    this.fheType = fheType;
    this.bitSize = bitSize;
    this.bitLength = bitLength;
    this.integer = integer;
    this.unsigned = unsigned;
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

  public boolean isInteger() {
    return integer;
  }

  public boolean isUnsigned() {
    return unsigned;
  }
}
