package io.github.rdlopes.tfhe.api.types.booleans;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.PublicKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.NativeCall.executeWithBoolean;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class FheBool extends NativeValue implements Cloneable {
  private static final Logger logger = LoggerFactory.getLogger(FheBool. class);

  /**
   * {@snippet lang = "c":
   * &#47;&#42;&#42;
   *  *ptr can be null (no-op in that case)
   *  &#42;&#47;
   * int fhe_bool_destroy(struct FheBool *ptr);
   *}
   */
  FheBool() {
    logger.trace("init");
    super(TfheHeader::fhe_bool_destroy);
  }

  /**
   * {@snippet lang = "c":
   * int fhe_bool_try_encrypt_with_client_key_bool(bool value,
   *                                               const struct ClientKey *client_key,
   *                                               struct FheBool **result);
   *}
   */
  public static FheBool encryptWithClientKey(Boolean clearValue, ClientKey clientKey) {
    logger.trace("encryptWithClientKey - clearValue: {}, clientKey: {}", clearValue, clientKey);
    FheBool encrypted = new FheBool();
    execute(() -> fhe_bool_try_encrypt_with_client_key_bool(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_try_encrypt_with_public_key_bool(bool value,
   *                                               const struct PublicKey *public_key,
   *                                               struct FheBool **result);
   *}
   */
  public static FheBool encryptWithPublicKey(Boolean clearValue, PublicKey publicKey) {
    logger.trace("encryptWithPublicKey - clearValue: {}, publicKey: {}", clearValue, publicKey);
    FheBool encrypted = new FheBool();
    execute(() -> fhe_bool_try_encrypt_with_public_key_bool(clearValue, publicKey.getValue(), encrypted.getAddress()));
    return encrypted;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_try_encrypt_trivial_bool(bool value, struct FheBool **result);
   *}
   */
  public static FheBool encryptTrivial(Boolean clearValue) {
    logger.trace("encryptTrivial - clearValue: {}", clearValue);
    FheBool encrypted = new FheBool();
    execute(() -> fhe_bool_try_encrypt_trivial_bool(clearValue, encrypted.getAddress()));
    return encrypted;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_decrypt(const struct FheBool *encrypted_value,
   *                      const struct ClientKey *client_key,
   *                      bool *result);
   *}
   */
  public Boolean decryptWithClientKey(ClientKey clientKey) {
    logger.trace("decryptWithClientKey - clientKey: {}", clientKey);
    return executeWithBoolean(address -> fhe_bool_decrypt(getValue(), clientKey.getValue(), address));
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_try_decrypt_trivial(const struct FheBool *encrypted_value, bool *result);
   *}
   */
  public Boolean decryptTrivial() {
    logger.trace("decryptTrivial");
    return executeWithBoolean(address -> fhe_bool_try_decrypt_trivial(getValue(), address));
  }

  /**
   * {@snippet lang = "c":
   * int fhe_bool_compress(const struct FheBool *sself, struct CompressedFheBool **result);
   *}
   */
  public CompressedFheBool compress() {
    CompressedFheBool compressed = new CompressedFheBool();
    logger.trace("compress");
    execute(() -> fhe_bool_compress(getValue(), compressed.getAddress()));
    return compressed;
  }

  /**
   * {@snippet lang = "c":
   * int fhe_bool_bitand(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
   *}
   */
  public FheBool and(FheBool other) {
    logger.trace("and - other: {}", other);
    FheBool result = new FheBool();
    execute(() -> fhe_bool_bitand(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_bitand_assign(struct FheBool *lhs, const struct FheBool *rhs);
   *}
   */
  public void andAssign(FheBool other) {
    logger.trace("andAssign - other: {}", other);
    execute(() -> fhe_bool_bitand_assign(getValue(), other.getValue()));
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_bitor(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
   *}
   */
  public FheBool or(FheBool other) {
    logger.trace("or - other: {}", other);
    FheBool result = new FheBool();
    execute(() -> fhe_bool_bitor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_bitor_assign(struct FheBool *lhs, const struct FheBool *rhs);
   *}
   */
  public void orAssign(FheBool other) {
    logger.trace("orAssign - other: {}", other);
    execute(() -> fhe_bool_bitor_assign(getValue(), other.getValue()));
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_bitxor(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
   *}
   */
  public FheBool xor(FheBool other) {
    logger.trace("xor - other: {}", other);
    FheBool result = new FheBool();
    execute(() -> fhe_bool_bitxor(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_bitxor_assign(struct FheBool *lhs, const struct FheBool *rhs);
   *}
   */
  public void xorAssign(FheBool other) {
    logger.trace("xorAssign - other: {}", other);
    execute(() -> fhe_bool_bitxor_assign(getValue(), other.getValue()));
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_not(const struct FheBool *input, struct FheBool **result);
   *}
   */
  public FheBool not() {
    logger.trace("not");
    FheBool result = new FheBool();
    execute(() -> fhe_bool_not(getValue(), result.getAddress()));
    return result;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_scalar_bitand(const struct FheBool *lhs, bool rhs, struct FheBool **result);
   *}
   */
  public FheBool scalarAnd(boolean scalar) {
    logger.trace("scalarAnd - scalar: {}", scalar);
    FheBool result = new FheBool();
    execute(() -> fhe_bool_scalar_bitand(getValue(), scalar, result.getAddress()));
    return result;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_scalar_bitand_assign(struct FheBool *lhs, bool rhs);
   *}
   */
  public void scalarAndAssign(boolean scalar) {
    logger.trace("scalarAndAssign - scalar: {}", scalar);
    execute(() -> fhe_bool_scalar_bitand_assign(getValue(), scalar));
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_scalar_bitor(const struct FheBool *lhs, bool rhs, struct FheBool **result);
   *}
   */
  public FheBool scalarOr(boolean scalar) {
    logger.trace("scalarOr - scalar: {}", scalar);
    FheBool result = new FheBool();
    execute(() -> fhe_bool_scalar_bitor(getValue(), scalar, result.getAddress()));
    return result;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_scalar_bitor_assign(struct FheBool *lhs, bool rhs);
   *}
   */
  public void scalarOrAssign(boolean scalar) {
    logger.trace("scalarOrAssign - scalar: {}", scalar);
    execute(() -> fhe_bool_scalar_bitor_assign(getValue(), scalar));
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_scalar_bitxor(const struct FheBool *lhs, bool rhs, struct FheBool **result);
   *}
   */
  public FheBool scalarXor(boolean scalar) {
    logger.trace("scalarXor - scalar: {}", scalar);
    FheBool result = new FheBool();
    execute(() -> fhe_bool_scalar_bitxor(getValue(), scalar, result.getAddress()));
    return result;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_scalar_bitxor_assign(struct FheBool *lhs, bool rhs);
   *}
   */
  public void scalarXorAssign(boolean scalar) {
    logger.trace("scalarXorAssign - scalar: {}", scalar);
    execute(() -> fhe_bool_scalar_bitxor_assign(getValue(), scalar));
  }

  /**
   * {@snippet lang = "c":
   * int fhe_bool_eq(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
   *}
   */
  public FheBool eq(FheBool other) {
    logger.trace("eq - other: {}", other);
    FheBool result = new FheBool();
    execute(() -> fhe_bool_eq(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_ne(const struct FheBool *lhs, const struct FheBool *rhs, struct FheBool **result);
   *}
   */
  public FheBool ne(FheBool other) {
    logger.trace("ne - other: {}", other);
    FheBool result = new FheBool();
    execute(() -> fhe_bool_ne(getValue(), other.getValue(), result.getAddress()));
    return result;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_scalar_eq(const struct FheBool *lhs, bool rhs, struct FheBool **result);
   *}
   */
  public FheBool scalarEq(Boolean scalar) {
    logger.trace("scalarEq - scalar: {}", scalar);
    FheBool result = new FheBool();
    execute(() -> fhe_bool_scalar_eq(getValue(), scalar, result.getAddress()));
    return result;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_scalar_ne(const struct FheBool *lhs, bool rhs, struct FheBool **result);
   *}
   */
  public FheBool scalarNe(Boolean scalar) {
    logger.trace("scalarNe - scalar: {}", scalar);
    FheBool result = new FheBool();
    execute(() -> fhe_bool_scalar_ne(getValue(), scalar, result.getAddress()));
    return result;
  }

  /**
   * {@snippet lang = "c":
   * &#47;&#42;&#42;
   *  * Serializes safely.
   *  *
   *  * This function adds versioning information to the serialized buffer, meaning that it will keep compatibility with future
   *  * versions of TFHE-rs.
   *  *
   *  * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
   *  *    (to avoid out of memory attacks)
   *  &#42;&#47;
   * int fhe_bool_safe_serialize(const struct FheBool *sself,
   *                             struct DynamicBuffer *result,
   *                             uint64_t serialized_size_limit);
   *}
   */
  public byte[] serialize() {
    logger.trace("serialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> fhe_bool_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  }
  /**
   * {@snippet lang = "c":
   * &#47;&#42;&#42;
   *  * Deserializes safely, and checks that the resulting ciphertext
   *  * is in compliance with the shape of ciphertext that the `server_key` expects.
   *  *
   *  * This function can only deserialize types which have been serialized
   *  * by a `safe_serialize` function.
   *  *
   *  * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
   *  *    (to avoid out of memory attacks)
   *  * - `server_key`: ServerKey used in the conformance check
   *  * - `result`: pointer where resulting deserialized object needs to be stored.
   *  *    * cannot be NULL
   *  *    * (*result) will point the deserialized object on success, else NULL
   *  &#42;&#47;
   * int fhe_bool_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
   *                                          uint64_t serialized_size_limit,
   *                                          const struct ServerKey *server_key,
   *                                          struct FheBool **result);
   *}
   */
  public static FheBool deserialize(byte[] buffer, ServerKey serverKey) {
    logger.trace("deserialize - buffer: {}, serverKey: {}", buffer, serverKey);
    DynamicBufferView dynamicBufferView = DynamicBufferView.fromByteArray(buffer);
    FheBool deserialized = new FheBool();
    execute(() -> fhe_bool_safe_deserialize_conformant(dynamicBufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()))
    ;
    return deserialized;
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_serialize(const struct FheBool *sself, struct DynamicBuffer *result);
   *}
   */
  public byte[] unsafeSerialize() {
    logger.trace("unsafeSerialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> fhe_bool_serialize(getValue(), dynamicBuffer.getAddress()));
      return dynamicBuffer.toByteArray();
    }
  }
  /**
   * {@snippet lang = "c":
   * int fhe_bool_deserialize(struct DynamicBufferView buffer_view, struct FheBool **result);
   *}
   */
  public static FheBool unsafeDeserialize(byte[] buffer) {
    logger.trace("unsafeDeserialize - buffer: {}", buffer);
    DynamicBufferView dynamicBufferView = DynamicBufferView.fromByteArray(buffer);
    FheBool deserialized = new FheBool();
    execute(() -> fhe_bool_deserialize(dynamicBufferView.getAddress(), deserialized.getAddress()));
    return deserialized;
  }

  /**
   * {@snippet lang = "c":
   * int fhe_bool_clone(const struct FheBool *sself, struct FheBool **result);
   *}
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public FheBool clone() {
    logger.trace("clone");
    FheBool clone = new FheBool();
    execute(() -> fhe_bool_clone(getValue(), clone.getAddress()));
    return clone;
  }

}

