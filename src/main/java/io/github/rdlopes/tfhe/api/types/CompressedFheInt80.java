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
public class CompressedFheInt80 extends NativePointer
implements CompressedFheType<I128, FheInt80, CompressedFheInt80> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt80.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int compressed_fhe_int80_destroy(struct CompressedFheInt80 *ptr);
  ///```
  CompressedFheInt80() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int80_destroy);
  }

  /// ```c
  /// int compressed_fhe_int80_decompress(const struct CompressedFheInt80 *sself,
  ///                                     struct FheInt80 **result);
  ///```
  @Override
  public FheInt80 decompress() {
    FheInt80 decompressed = new FheInt80();
    execute(() -> compressed_fhe_int80_decompress(getValue(), decompressed.getAddress()));
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
  /// int compressed_fhe_int80_safe_serialize(const struct CompressedFheInt80 *sself,
  ///                                         struct DynamicBuffer *result,
  ///                                         uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize(){
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int80_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

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
  /// int compressed_fhe_int80_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                                      uint64_t serialized_size_limit,
  ///                                                      const struct ServerKey *server_key,
  ///                                                      struct CompressedFheInt80 **result);
  ///```
  public static CompressedFheInt80 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    CompressedFheInt80 deserialized = new CompressedFheInt80();
    execute(() -> compressed_fhe_int80_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /// ```c
  /// int compressed_fhe_int80_try_encrypt_with_client_key_i128(struct I128 value,
  ///                                                           const struct ClientKey *client_key,
  ///                                                           struct CompressedFheInt80 **result);
  ///```
  public static CompressedFheInt80 encrypt(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt80 encrypted = new CompressedFheInt80();
    execute(() -> compressed_fhe_int80_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int compressed_fhe_int80_clone(const struct CompressedFheInt80 *sself,
  ///                                struct CompressedFheInt80 **result);
/// ```
@Override
@SuppressWarnings("MethodDoesntCallSuperMethod")
public CompressedFheInt80 clone(){
    CompressedFheInt80 cloned = new CompressedFheInt80();
    execute(() -> compressed_fhe_int80_clone(getValue(), cloned.getAddress()));
    return cloned;

}
}
