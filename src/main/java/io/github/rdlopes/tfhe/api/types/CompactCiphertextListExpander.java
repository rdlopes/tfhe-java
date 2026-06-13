package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompactCiphertextListExpander extends NativePointer implements AutoCloseable {
  private static final Logger logger = LoggerFactory.getLogger(CompactCiphertextListExpander.class);

  public CompactCiphertextListExpander() {
    super(TfheHeader::compact_ciphertext_list_expander_destroy);
  }

  public long size() {
    logger.trace("size");
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment sizePtr = arena.allocate(ValueLayout.JAVA_LONG);
      execute(() -> compact_ciphertext_list_expander_len(getValue(), sizePtr));
      return sizePtr.get(ValueLayout.JAVA_LONG, 0);
    }
  }

  public FheTypes getKindOf(long index) {
    logger.trace("getKindOf - index: {}", index);
    try (Arena arena = Arena.ofConfined()) {
      MemorySegment kindPtr = arena.allocate(ValueLayout.JAVA_INT);
      execute(() -> compact_ciphertext_list_expander_get_kind_of(getValue(), index, kindPtr));
      int kindVal = kindPtr.get(ValueLayout.JAVA_INT, 0);
      return FheTypes.valueOrdered(kindVal);
    }
  }

  public FheBool getFheBool(long index) {
    logger.trace("getFheBool - index: {}", index);
    FheBool result = new FheBool();
    execute(() -> compact_ciphertext_list_expander_get_fhe_bool(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt8 getFheInt8(long index) {
    logger.trace("getFheInt8 - index: {}", index);
    FheInt8 result = new FheInt8();
    execute(() -> compact_ciphertext_list_expander_get_fhe_int8(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt16 getFheInt16(long index) {
    logger.trace("getFheInt16 - index: {}", index);
    FheInt16 result = new FheInt16();
    execute(() -> compact_ciphertext_list_expander_get_fhe_int16(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt32 getFheInt32(long index) {
    logger.trace("getFheInt32 - index: {}", index);
    FheInt32 result = new FheInt32();
    execute(() -> compact_ciphertext_list_expander_get_fhe_int32(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt64 getFheInt64(long index) {
    logger.trace("getFheInt64 - index: {}", index);
    FheInt64 result = new FheInt64();
    execute(() -> compact_ciphertext_list_expander_get_fhe_int64(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt128 getFheInt128(long index) {
    logger.trace("getFheInt128 - index: {}", index);
    FheInt128 result = new FheInt128();
    execute(() -> compact_ciphertext_list_expander_get_fhe_int128(getValue(), index, result.getAddress()));
    return result;
  }

  public FheInt256 getFheInt256(long index) {
    logger.trace("getFheInt256 - index: {}", index);
    FheInt256 result = new FheInt256();
    execute(() -> compact_ciphertext_list_expander_get_fhe_int256(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint8 getFheUint8(long index) {
    logger.trace("getFheUint8 - index: {}", index);
    FheUint8 result = new FheUint8();
    execute(() -> compact_ciphertext_list_expander_get_fhe_uint8(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint16 getFheUint16(long index) {
    logger.trace("getFheUint16 - index: {}", index);
    FheUint16 result = new FheUint16();
    execute(() -> compact_ciphertext_list_expander_get_fhe_uint16(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint32 getFheUint32(long index) {
    logger.trace("getFheUint32 - index: {}", index);
    FheUint32 result = new FheUint32();
    execute(() -> compact_ciphertext_list_expander_get_fhe_uint32(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint64 getFheUint64(long index) {
    logger.trace("getFheUint64 - index: {}", index);
    FheUint64 result = new FheUint64();
    execute(() -> compact_ciphertext_list_expander_get_fhe_uint64(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint128 getFheUint128(long index) {
    logger.trace("getFheUint128 - index: {}", index);
    FheUint128 result = new FheUint128();
    execute(() -> compact_ciphertext_list_expander_get_fhe_uint128(getValue(), index, result.getAddress()));
    return result;
  }

  public FheUint256 getFheUint256(long index) {
    logger.trace("getFheUint256 - index: {}", index);
    FheUint256 result = new FheUint256();
    execute(() -> compact_ciphertext_list_expander_get_fhe_uint256(getValue(), index, result.getAddress()));
    return result;
  }

  @Override
  public void close() {
    destroy();
  }
}
