package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.CompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

// @formatter:off
public class CompressedFheUint192 extends NativePointer
implements CompressedFheType<U256, FheUint192, CompressedFheUint192> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint192.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int compressed_fhe_uint192_destroy(struct CompressedFheUint192 *ptr);
  ///```
  CompressedFheUint192() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint192_destroy);
  }

  /// ```c
  /// int compressed_fhe_uint192_decompress(const struct CompressedFheUint192 *sself,
  ///                                       struct FheUint192 **result);
  ///```
  @Override
  public FheUint192 decompress() {
    FheUint192 decompressed = new FheUint192();
    execute(() -> compressed_fhe_uint192_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /// ```c
  ////**
  ///  * Serializes safely.
  ///  *
  ///  * This function adds versioning information to the serialized buffer, meaning that it will keep compatibility with future
  ///  * versions of TFHE-rs.
  ///  *
  ///  * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
  ///  *    (to avoid out of memory attacks)
  ///  */
  /// int compressed_fhe_uint192_safe_serialize(const struct CompressedFheUint192 *sself,
  ///                                           struct DynamicBuffer *result,
  ///                                           uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint192_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /// ```c
  ////**
  ///  * Deserializes safely, and checks that the resulting ciphertext
  ///  * is in compliance with the shape of ciphertext that the `server_key` expects.
  ///  *
  ///  * This function can only deserialize types which have been serialized
  ///  * by a `safe_serialize` function.
  ///  *
  ///  * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
  ///  *    (to avoid out of memory attacks)
  ///  * - `server_key`: ServerKey used in the conformance check
  ///  * - `result`: pointer where resulting deserialized object needs to be stored.
  ///  *    * cannot be NULL
  ///  *    * (*result) will point the deserialized object on success, else NULL
  ///  */
  /// int compressed_fhe_uint192_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                                        uint64_t serialized_size_limit,
  ///                                                        const struct ServerKey *server_key,
  ///                                                        struct CompressedFheUint192 **result);
  ///```
  public static CompressedFheUint192 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint192 deserialized = new CompressedFheUint192();
    execute(() -> compressed_fhe_uint192_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /// ```c
  /// int compressed_fhe_uint192_try_encrypt_with_client_key_u256(struct U256 value,
  ///                                                             const struct ClientKey *client_key,
  ///                                                             struct CompressedFheUint192 **result);
  ///```
  public static CompressedFheUint192 encrypt(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint192 encrypted = new CompressedFheUint192();
    execute(() -> compressed_fhe_uint192_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int compressed_fhe_uint192_clone(const struct CompressedFheUint192 *sself,
  ///                                  struct CompressedFheUint192 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint192 clone() {
    CompressedFheUint192 cloned = new CompressedFheUint192();
    execute(() -> compressed_fhe_uint192_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
