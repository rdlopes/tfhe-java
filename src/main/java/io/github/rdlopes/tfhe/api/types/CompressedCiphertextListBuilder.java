package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompressedCiphertextListBuilder extends NativePointer implements AutoCloseable {
  private static final Logger logger = LoggerFactory.getLogger(CompressedCiphertextListBuilder.class);

  public CompressedCiphertextListBuilder() {
    super(builder -> {
      TfheHeader.compressed_ciphertext_list_builder_destroy(builder);
      return 0;
    });
    logger.trace("init");
    execute(() -> compressed_ciphertext_list_builder_new(getAddress()));
  }

  public CompressedCiphertextList build() {
    logger.trace("build");
    CompressedCiphertextList result = new CompressedCiphertextList();
    execute(() -> compressed_ciphertext_list_builder_build(getValue(), result.getAddress()));
    return result;
  }

  public void push(FheBool value) {
    logger.trace("push - FheBool");
    execute(() -> compressed_ciphertext_list_builder_push_bool(getValue(), value.getValue()));
  }

  // Integer types (signed)
  public void push(FheInt2 value) {
    logger.trace("push - FheInt2");
    execute(() -> compressed_ciphertext_list_builder_push_i2(getValue(), value.getValue()));
  }

  public void push(FheInt4 value) {
    logger.trace("push - FheInt4");
    execute(() -> compressed_ciphertext_list_builder_push_i4(getValue(), value.getValue()));
  }

  public void push(FheInt6 value) {
    logger.trace("push - FheInt6");
    execute(() -> compressed_ciphertext_list_builder_push_i6(getValue(), value.getValue()));
  }

  public void push(FheInt8 value) {
    logger.trace("push - FheInt8");
    execute(() -> compressed_ciphertext_list_builder_push_i8(getValue(), value.getValue()));
  }

  public void push(FheInt10 value) {
    logger.trace("push - FheInt10");
    execute(() -> compressed_ciphertext_list_builder_push_i10(getValue(), value.getValue()));
  }

  public void push(FheInt12 value) {
    logger.trace("push - FheInt12");
    execute(() -> compressed_ciphertext_list_builder_push_i12(getValue(), value.getValue()));
  }

  public void push(FheInt14 value) {
    logger.trace("push - FheInt14");
    execute(() -> compressed_ciphertext_list_builder_push_i14(getValue(), value.getValue()));
  }

  public void push(FheInt16 value) {
    logger.trace("push - FheInt16");
    execute(() -> compressed_ciphertext_list_builder_push_i16(getValue(), value.getValue()));
  }

  public void push(FheInt32 value) {
    logger.trace("push - FheInt32");
    execute(() -> compressed_ciphertext_list_builder_push_i32(getValue(), value.getValue()));
  }

  public void push(FheInt64 value) {
    logger.trace("push - FheInt64");
    execute(() -> compressed_ciphertext_list_builder_push_i64(getValue(), value.getValue()));
  }

  public void push(FheInt128 value) {
    logger.trace("push - FheInt128");
    execute(() -> compressed_ciphertext_list_builder_push_i128(getValue(), value.getValue()));
  }

  public void push(FheInt160 value) {
    logger.trace("push - FheInt160");
    execute(() -> compressed_ciphertext_list_builder_push_i160(getValue(), value.getValue()));
  }

  public void push(FheInt256 value) {
    logger.trace("push - FheInt256");
    execute(() -> compressed_ciphertext_list_builder_push_i256(getValue(), value.getValue()));
  }

  // Integer types (unsigned)
  public void push(FheUint2 value) {
    logger.trace("push - FheUint2");
    execute(() -> compressed_ciphertext_list_builder_push_u2(getValue(), value.getValue()));
  }

  public void push(FheUint4 value) {
    logger.trace("push - FheUint4");
    execute(() -> compressed_ciphertext_list_builder_push_u4(getValue(), value.getValue()));
  }

  public void push(FheUint6 value) {
    logger.trace("push - FheUint6");
    execute(() -> compressed_ciphertext_list_builder_push_u6(getValue(), value.getValue()));
  }

  public void push(FheUint8 value) {
    logger.trace("push - FheUint8");
    execute(() -> compressed_ciphertext_list_builder_push_u8(getValue(), value.getValue()));
  }

  public void push(FheUint10 value) {
    logger.trace("push - FheUint10");
    execute(() -> compressed_ciphertext_list_builder_push_u10(getValue(), value.getValue()));
  }

  public void push(FheUint12 value) {
    logger.trace("push - FheUint12");
    execute(() -> compressed_ciphertext_list_builder_push_u12(getValue(), value.getValue()));
  }

  public void push(FheUint14 value) {
    logger.trace("push - FheUint14");
    execute(() -> compressed_ciphertext_list_builder_push_u14(getValue(), value.getValue()));
  }

  public void push(FheUint16 value) {
    logger.trace("push - FheUint16");
    execute(() -> compressed_ciphertext_list_builder_push_u16(getValue(), value.getValue()));
  }

  public void push(FheUint32 value) {
    logger.trace("push - FheUint32");
    execute(() -> compressed_ciphertext_list_builder_push_u32(getValue(), value.getValue()));
  }

  public void push(FheUint64 value) {
    logger.trace("push - FheUint64");
    execute(() -> compressed_ciphertext_list_builder_push_u64(getValue(), value.getValue()));
  }

  public void push(FheUint128 value) {
    logger.trace("push - FheUint128");
    execute(() -> compressed_ciphertext_list_builder_push_u128(getValue(), value.getValue()));
  }

  public void push(FheUint160 value) {
    logger.trace("push - FheUint160");
    execute(() -> compressed_ciphertext_list_builder_push_u160(getValue(), value.getValue()));
  }

  public void push(FheUint256 value) {
    logger.trace("push - FheUint256");
    execute(() -> compressed_ciphertext_list_builder_push_u256(getValue(), value.getValue()));
  }

  @Override
  public void close() {
    destroy();
  }
}
