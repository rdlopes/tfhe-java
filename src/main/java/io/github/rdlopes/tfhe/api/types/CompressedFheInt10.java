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
public class CompressedFheInt10 extends NativePointer
implements CompressedFheType<Short, FheInt10, CompressedFheInt10> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt10.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int compressed_fhe_int10_destroy(struct CompressedFheInt10 *ptr);
  ///```
  CompressedFheInt10() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int10_destroy);
  }

  /// ```c
  /// int compressed_fhe_int10_decompress(const struct CompressedFheInt10 *sself,
  ///                                     struct FheInt10 **result);
  ///```
  @Override
  public FheInt10 decompress() {
    FheInt10 decompressed = new FheInt10();
    execute(() -> compressed_fhe_int10_decompress(getValue(), decompressed.getAddress()));
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
  /// int compressed_fhe_int10_safe_serialize(const struct CompressedFheInt10 *sself,
  ///                                         struct DynamicBuffer *result,
  ///                                         uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize(){
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int10_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

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
  /// int compressed_fhe_int10_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                                      uint64_t serialized_size_limit,
  ///                                                      const struct ServerKey *server_key,
  ///                                                      struct CompressedFheInt10 **result);
  ///```
  public static CompressedFheInt10 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    CompressedFheInt10 deserialized = new CompressedFheInt10();
    execute(() -> compressed_fhe_int10_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /// ```c
  /// int compressed_fhe_int10_try_encrypt_with_client_key_i16(int16_t value,
  ///                                                          const struct ClientKey *client_key,
  ///                                                          struct CompressedFheInt10 **result);
  ///```
  public static CompressedFheInt10 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheInt10 encrypted = new CompressedFheInt10();
    execute(() -> compressed_fhe_int10_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int compressed_fhe_int10_clone(const struct CompressedFheInt10 *sself,
///                                struct CompressedFheInt10 **result);
/// ```
@Override
@SuppressWarnings("MethodDoesntCallSuperMethod")
public CompressedFheInt10 clone(){
    CompressedFheInt10 cloned = new CompressedFheInt10();
    execute(() -> compressed_fhe_int10_clone(getValue(), cloned.getAddress()));
    return cloned;

}
}
