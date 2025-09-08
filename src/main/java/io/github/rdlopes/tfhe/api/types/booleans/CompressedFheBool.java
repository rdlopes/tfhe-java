package io.github.rdlopes.tfhe.api.types.booleans;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompressedFheBool extends NativeValue implements Cloneable {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheBool.class);

  /**
   * {@snippet lang = "c":
   * &#47;&#42;&#42;
   *  &#42;ptr can be null (no-op in that case)
   *  &#42;&#47;
   * int compressed_fhe_bool_destroy(struct CompressedFheBool &#42;ptr);
   *}
   */
  CompressedFheBool() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_bool_destroy);
  }

  /**
   * {@snippet lang = "c":
   * int compressed_fhe_bool_try_encrypt_with_client_key_bool(bool value,
   *                                                          const struct ClientKey &#42;client_key,
   *                                                          struct CompressedFheBool &#42;&#42;result);
   *}
   */
  public static CompressedFheBool encryptWithClientKey(Boolean clearValue, ClientKey clientKey) {
    logger.trace("encryptWithClientKey - clearValue: {}, clientKey: {}", clearValue, clientKey);
    CompressedFheBool encrypted = new CompressedFheBool();
    execute(() -> compressed_fhe_bool_try_encrypt_with_client_key_bool(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;
  }

  /**
   * {@snippet lang = "c":
   * &#47;&#42;&#42;
   *  &#42; Deserializes safely, and checks that the resulting ciphertext
   *  &#42; is in compliance with the shape of ciphertext that the `server_key` expects.
   *  &#42;
   *  &#42; This function can only deserialize types which have been serialized
   *  &#42; by a `safe_serialize` function.
   *  &#42;
   *  &#42; - `serialized_size_limit`: size limit (in number of byte) of the serialized object
   *  &#42;    (to avoid out of memory attacks)
   *  &#42; - `server_key`: ServerKey used in the conformance check
   *  &#42; - `result`: pointer where resulting deserialized object needs to be stored.
   *  &#42;    &#42; cannot be NULL
   *  &#42;    &#42; (&#42;result) will point the deserialized object on success, else NULL
   *  &#42;&#47;
   * int compressed_fhe_bool_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
   *                                                     uint64_t serialized_size_limit,
   *                                                     const struct ServerKey &#42;server_key,
   *                                                     struct CompressedFheBool &#42;&#42;result);
   *}
   */
  public static CompressedFheBool deserialize(byte[] buffer, ServerKey serverKey) {
    logger.trace("deserialize - buffer: {}, serverKey: {}", buffer, serverKey);
    DynamicBufferView dynamicBufferView = DynamicBufferView.fromByteArray(buffer);
    CompressedFheBool deserialized = new CompressedFheBool();
    execute(() -> compressed_fhe_bool_safe_deserialize_conformant(dynamicBufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()))
    ;
    return deserialized;
  }

  /**
   * {@snippet lang = "c":
   * &#47;&#42;&#42;
   *  &#42; Serializes safely.
   *  &#42;
   *  &#42; This function adds versioning information to the serialized buffer, meaning that it will keep compatibility with future
   *  &#42; versions of TFHE-rs.
   *  &#42;
   *  &#42; - `serialized_size_limit`: size limit (in number of byte) of the serialized object
   *  &#42;    (to avoid out of memory attacks)
   *  &#42;&#47;
   * int compressed_fhe_bool_safe_serialize(const struct CompressedFheBool &#42;sself,
   *                                        struct DynamicBuffer &#42;result,
   *                                        uint64_t serialized_size_limit);
   *}
   */
  public byte[] serialize() {
    logger.trace("serialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> compressed_fhe_bool_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  }

  /**
   * {@snippet lang = "c":
   * int compressed_fhe_bool_serialize(const struct CompressedFheBool &#42;sself,
   *                                   struct DynamicBuffer &#42;result);
   *}
   */
  public byte[] unsafeSerialize() {
    logger.trace("unsafeSerialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> compressed_fhe_bool_serialize(getValue(), dynamicBuffer.getAddress()));
      return dynamicBuffer.toByteArray();
    }
  }

  /**
   * {@snippet lang = "c":
   * int compressed_fhe_bool_deserialize(struct DynamicBufferView buffer_view,
   *                                     struct CompressedFheBool &#42;&#42;result);
   *}
   */
  public static CompressedFheBool unsafeDeserialize(byte[] buffer) {
    logger.trace("unsafeDeserialize - buffer: {}", buffer);
    DynamicBufferView dynamicBufferView = DynamicBufferView.fromByteArray(buffer);
    CompressedFheBool deserialized = new CompressedFheBool();
    execute(() -> compressed_fhe_bool_deserialize(dynamicBufferView.getAddress(), deserialized.getAddress()));
    return deserialized;
  }

  /**
   * {@snippet lang = "c":
   * int compressed_fhe_bool_decompress(const struct CompressedFheBool &#42;sself, struct FheBool &#42;&#42;result);
   *}
   */
  public FheBool decompress() {
    logger.trace("decompress");
    FheBool decompressed = new FheBool();
    execute(() -> compressed_fhe_bool_decompress(getValue(), decompressed.getAddress()));
    return decompressed;
  }

  /**
   * {@snippet lang = "c":
   * int compressed_fhe_bool_clone(const struct CompressedFheBool &#42;sself,
   *                               struct CompressedFheBool &#42;&#42;result);
   *}
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheBool clone() {
    logger.trace("clone");
    CompressedFheBool clone = new CompressedFheBool();
    execute(() -> compressed_fhe_bool_clone(getValue(), clone.getAddress()));
    return clone;
  }

}
