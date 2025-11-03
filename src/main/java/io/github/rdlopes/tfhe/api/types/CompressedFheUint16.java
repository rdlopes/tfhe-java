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
public class CompressedFheUint16 extends NativePointer implements CompressedFheType<Short, FheUint16, CompressedFheUint16> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint16.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int compressed_fhe_uint16_destroy(struct CompressedFheUint16 *ptr);
  ///```
  CompressedFheUint16() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint16_destroy);
  }

  /// ```c
  /// int compressed_fhe_uint16_decompress(const struct CompressedFheUint16 *sself,
  ///                                      struct FheUint16 **result);
  ///```
  @Override
  public FheUint16 decompress() {
    FheUint16 decompressed = new FheUint16();
    execute(() -> compressed_fhe_uint16_decompress(getValue(), decompressed.getAddress()));
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
  /// int compressed_fhe_uint16_safe_serialize(const struct CompressedFheUint16 *sself,
  ///                                          struct DynamicBuffer *result,
  ///                                          uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize(){
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint16_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

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
  /// int compressed_fhe_uint16_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                                       uint64_t serialized_size_limit,
  ///                                                       const struct ServerKey *server_key,
  ///                                                       struct CompressedFheUint16 **result);
  ///```
  public static CompressedFheUint16 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    CompressedFheUint16 deserialized = new CompressedFheUint16();
    execute(() -> compressed_fhe_uint16_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /// ```c
  /// int compressed_fhe_uint16_try_encrypt_with_client_key_u16(uint16_t value,
  ///                                                           const struct ClientKey *client_key,
  ///                                                           struct CompressedFheUint16 **result);
  ///```
  public static CompressedFheUint16 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheUint16 encrypted = new CompressedFheUint16();
    execute(() -> compressed_fhe_uint16_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int compressed_fhe_uint16_clone(const struct CompressedFheUint16 *sself,
  ///                                 struct CompressedFheUint16 **result);
  ///```
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
public CompressedFheUint16 clone(){
    CompressedFheUint16 cloned = new CompressedFheUint16();
    execute(() -> compressed_fhe_uint16_clone(getValue(), cloned.getAddress()));
    return cloned;

}
}
