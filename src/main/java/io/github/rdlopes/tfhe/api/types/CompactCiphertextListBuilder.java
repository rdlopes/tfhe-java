package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.keys.CompactPublicKey;
import io.github.rdlopes.tfhe.api.values.*;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompactCiphertextListBuilder extends NativePointer {
  private static final Logger logger = LoggerFactory.getLogger(CompactCiphertextListBuilder.class);

  public CompactCiphertextListBuilder(CompactPublicKey compactPublicKey) {
    super(TfheHeader::compact_ciphertext_list_builder_destroy);
    logger.trace("init");
    execute(() -> compact_ciphertext_list_builder_new(compactPublicKey.getValue(), getAddress()));
  }

  public CompactCiphertextList build() {
    logger.trace("build");
    CompactCiphertextList result = new CompactCiphertextList();
    execute(() -> compact_ciphertext_list_builder_build(getValue(), result.getAddress()));
    return result;
  }

  public CompactCiphertextList buildPacked() {
    logger.trace("buildPacked");
    CompactCiphertextList result = new CompactCiphertextList();
    execute(() -> compact_ciphertext_list_builder_build_packed(getValue(), result.getAddress()));
    return result;
  }

  public void push(boolean value) {
    logger.trace("push - boolean: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_bool(getValue(), value));
  }

  public void push(byte value) {
    logger.trace("push - byte: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_i8(getValue(), value));
  }

  public void push(short value) {
    logger.trace("push - short: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_i16(getValue(), value));
  }

  public void push(int value) {
    logger.trace("push - int: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_i32(getValue(), value));
  }

  public void push(long value) {
    logger.trace("push - long: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_i64(getValue(), value));
  }

  public void push(I128 value) {
    logger.trace("push - I128: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_i128(getValue(), value.getAddress()));
  }

  public void push(I256 value) {
    logger.trace("push - I256: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_i256(getValue(), value.getAddress()));
  }

  public void pushUnsigned(byte value) {
    logger.trace("pushUnsigned - byte: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_u8(getValue(), value));
  }

  public void pushUnsigned(short value) {
    logger.trace("pushUnsigned - short: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_u16(getValue(), value));
  }

  public void pushUnsigned(int value) {
    logger.trace("pushUnsigned - int: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_u32(getValue(), value));
  }

  public void pushUnsigned(long value) {
    logger.trace("pushUnsigned - long: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_u64(getValue(), value));
  }

  public void push(U128 value) {
    logger.trace("push - U128: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_u128(getValue(), value.getAddress()));
  }

  public void push(U256 value) {
    logger.trace("push - U256: {}", value);
    execute(() -> compact_ciphertext_list_builder_push_u256(getValue(), value.getAddress()));
  }
}
