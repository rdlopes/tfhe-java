package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.FheObject;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompressedCiphertextList extends NativePointer implements FheObject, AutoCloseable {
  private static final Logger logger = LoggerFactory.getLogger(CompressedCiphertextList.class);

  public CompressedCiphertextList() {
    super(TfheHeader::compressed_ciphertext_list_destroy);
  }

  public static CompressedCiphertextList deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize");
    CompressedCiphertextList deserialized = new CompressedCiphertextList();
    execute(() -> compressed_ciphertext_list_safe_deserialize(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_ciphertext_list_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    return dynamicBuffer;
  }

  public long size() {
    logger.trace("size");
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment sizePtr = arena.allocate(ValueLayout.JAVA_LONG);
      execute(() -> compressed_ciphertext_list_len(getValue(), sizePtr));
      return sizePtr.get(ValueLayout.JAVA_LONG, 0);
    }
  }

  public FheTypes getKindOf(long index) {
    logger.trace("getKindOf - index: {}", index);
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment kindPtr = arena.allocate(ValueLayout.JAVA_INT);
      execute(() -> compressed_ciphertext_list_get_kind_of(getValue(), index, kindPtr));
      int kindVal = kindPtr.get(ValueLayout.JAVA_INT, 0);
      return FheTypes.valueOrdered(kindVal);
    }
  }

  public FheBool getFheBool(long index) {
    logger.trace("getFheBool - index: {}", index);
    FheBool result = new FheBool();
    execute(() -> compressed_ciphertext_list_get_fhe_bool(getValue(), index, result.getAddress()));
    return result;
  }

  // Getters for signed integer types
  public FheInt2 getFheInt2(long index) {
    logger.trace("getFheInt2 - index: {}", index);
    FheInt2 result = new FheInt2();
    execute(() -> compressed_ciphertext_list_get_fhe_int2(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt4 getFheInt4(long index) {
    logger.trace("getFheInt4 - index: {}", index);
    FheInt4 result = new FheInt4();
    execute(() -> compressed_ciphertext_list_get_fhe_int4(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt6 getFheInt6(long index) {
    logger.trace("getFheInt6 - index: {}", index);
    FheInt6 result = new FheInt6();
    execute(() -> compressed_ciphertext_list_get_fhe_int6(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt8 getFheInt8(long index) {
    logger.trace("getFheInt8 - index: {}", index);
    FheInt8 result = new FheInt8();
    execute(() -> compressed_ciphertext_list_get_fhe_int8(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt10 getFheInt10(long index) {
    logger.trace("getFheInt10 - index: {}", index);
    FheInt10 result = new FheInt10();
    execute(() -> compressed_ciphertext_list_get_fhe_int10(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt12 getFheInt12(long index) {
    logger.trace("getFheInt12 - index: {}", index);
    FheInt12 result = new FheInt12();
    execute(() -> compressed_ciphertext_list_get_fhe_int12(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt14 getFheInt14(long index) {
    logger.trace("getFheInt14 - index: {}", index);
    FheInt14 result = new FheInt14();
    execute(() -> compressed_ciphertext_list_get_fhe_int14(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt16 getFheInt16(long index) {
    logger.trace("getFheInt16 - index: {}", index);
    FheInt16 result = new FheInt16();
    execute(() -> compressed_ciphertext_list_get_fhe_int16(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt32 getFheInt32(long index) {
    logger.trace("getFheInt32 - index: {}", index);
    FheInt32 result = new FheInt32();
    execute(() -> compressed_ciphertext_list_get_fhe_int32(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt64 getFheInt64(long index) {
    logger.trace("getFheInt64 - index: {}", index);
    FheInt64 result = new FheInt64();
    execute(() -> compressed_ciphertext_list_get_fhe_int64(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt128 getFheInt128(long index) {
    logger.trace("getFheInt128 - index: {}", index);
    FheInt128 result = new FheInt128();
    execute(() -> compressed_ciphertext_list_get_fhe_int128(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt160 getFheInt160(long index) {
    logger.trace("getFheInt160 - index: {}", index);
    FheInt160 result = new FheInt160();
    execute(() -> compressed_ciphertext_list_get_fhe_int160(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt256 getFheInt256(long index) {
    logger.trace("getFheInt256 - index: {}", index);
    FheInt256 result = new FheInt256();
    execute(() -> compressed_ciphertext_list_get_fhe_int256(getValue(), index, result.getAddress()));
    return result;
  }

  // Getters for unsigned integer types
  public FheUint2 getFheUint2(long index) {
    logger.trace("getFheUint2 - index: {}", index);
    FheUint2 result = new FheUint2();
    execute(() -> compressed_ciphertext_list_get_fhe_uint2(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint4 getFheUint4(long index) {
    logger.trace("getFheUint4 - index: {}", index);
    FheUint4 result = new FheUint4();
    execute(() -> compressed_ciphertext_list_get_fhe_uint4(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint6 getFheUint6(long index) {
    logger.trace("getFheUint6 - index: {}", index);
    FheUint6 result = new FheUint6();
    execute(() -> compressed_ciphertext_list_get_fhe_uint6(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint8 getFheUint8(long index) {
    logger.trace("getFheUint8 - index: {}", index);
    FheUint8 result = new FheUint8();
    execute(() -> compressed_ciphertext_list_get_fhe_uint8(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint10 getFheUint10(long index) {
    logger.trace("getFheUint10 - index: {}", index);
    FheUint10 result = new FheUint10();
    execute(() -> compressed_ciphertext_list_get_fhe_uint10(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint12 getFheUint12(long index) {
    logger.trace("getFheUint12 - index: {}", index);
    FheUint12 result = new FheUint12();
    execute(() -> compressed_ciphertext_list_get_fhe_uint12(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint14 getFheUint14(long index) {
    logger.trace("getFheUint14 - index: {}", index);
    FheUint14 result = new FheUint14();
    execute(() -> compressed_ciphertext_list_get_fhe_uint14(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint16 getFheUint16(long index) {
    logger.trace("getFheUint16 - index: {}", index);
    FheUint16 result = new FheUint16();
    execute(() -> compressed_ciphertext_list_get_fhe_uint16(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint32 getFheUint32(long index) {
    logger.trace("getFheUint32 - index: {}", index);
    FheUint32 result = new FheUint32();
    execute(() -> compressed_ciphertext_list_get_fhe_uint32(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint64 getFheUint64(long index) {
    logger.trace("getFheUint64 - index: {}", index);
    FheUint64 result = new FheUint64();
    execute(() -> compressed_ciphertext_list_get_fhe_uint64(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint128 getFheUint128(long index) {
    logger.trace("getFheUint128 - index: {}", index);
    FheUint128 result = new FheUint128();
    execute(() -> compressed_ciphertext_list_get_fhe_uint128(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint160 getFheUint160(long index) {
    logger.trace("getFheUint160 - index: {}", index);
    FheUint160 result = new FheUint160();
    execute(() -> compressed_ciphertext_list_get_fhe_uint160(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint256 getFheUint256(long index) {
    logger.trace("getFheUint256 - index: {}", index);
    FheUint256 result = new FheUint256();
    execute(() -> compressed_ciphertext_list_get_fhe_uint256(getValue(), index, result.getAddress()));
    return result;
  }

  @Override
  public void close() {
    destroy();
  }
}
