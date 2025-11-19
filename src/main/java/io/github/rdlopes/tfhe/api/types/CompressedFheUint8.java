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
public class CompressedFheUint8 extends NativePointer implements CompressedFheType<Byte, FheUint8, CompressedFheUint8> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint8.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int compressed_fhe_uint8_destroy(struct CompressedFheUint8 *ptr);
  ///```
  CompressedFheUint8() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint8_destroy);
  }

  /// ```c
  /// int compressed_fhe_uint8_decompress(const struct CompressedFheUint8 *sself,
  ///                                     struct FheUint8 **result);
  ///```
  @Override
  public FheUint8 decompress() {
    FheUint8 decompressed = new FheUint8();
    execute(() -> compressed_fhe_uint8_decompress(getValue(), decompressed.getAddress()));
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
  /// int compressed_fhe_uint8_safe_serialize(const struct CompressedFheUint8 *sself,
  ///                                         struct DynamicBuffer *result,
  ///                                         uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize(){
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint8_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

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
  /// int compressed_fhe_uint8_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                                      uint64_t serialized_size_limit,
  ///                                                      const struct ServerKey *server_key,
  ///                                                      struct CompressedFheUint8 **result);
  ///```
  public static CompressedFheUint8 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    CompressedFheUint8 deserialized = new CompressedFheUint8();
    execute(() -> compressed_fhe_uint8_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /// ```c
  /// int compressed_fhe_uint8_try_encrypt_with_client_key_u8(uint8_t value,
  ///                                                         const struct ClientKey *client_key,
  ///                                                         struct CompressedFheUint8 **result);
  ///```
  public static CompressedFheUint8 encrypt(Byte clearValue, ClientKey clientKey) {
    CompressedFheUint8 encrypted = new CompressedFheUint8();
    execute(() -> compressed_fhe_uint8_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int compressed_fhe_uint8_clone(const struct CompressedFheUint8 *sself,
  ///                                struct CompressedFheUint8 **result);
/// ```
@Override
@SuppressWarnings("MethodDoesntCallSuperMethod")
public CompressedFheUint8 clone(){
    CompressedFheUint8 cloned = new CompressedFheUint8();
    execute(() -> compressed_fhe_uint8_clone(getValue(), cloned.getAddress()));
    return cloned;

}
}
