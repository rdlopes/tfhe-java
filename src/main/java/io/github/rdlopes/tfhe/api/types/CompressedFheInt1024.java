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
public class CompressedFheInt1024 extends NativePointer
implements CompressedFheType<I1024, FheInt1024, CompressedFheInt1024> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt1024.class);
// @formatter:on

  /// ```c
  ////**
  ///  *ptr can be null (no-op in that case)
  ///  */
  /// int compressed_fhe_int1024_destroy(struct CompressedFheInt1024 *ptr);
  ///```
  CompressedFheInt1024() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int1024_destroy);
  }

  /// ```c
  /// int compressed_fhe_int1024_decompress(const struct CompressedFheInt1024 *sself,
  ///                                       struct FheInt1024 **result);
  ///```
  @Override
  public FheInt1024 decompress() {
    FheInt1024 decompressed = new FheInt1024();
    execute(() -> compressed_fhe_int1024_decompress(getValue(), decompressed.getAddress()));
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
  /// int compressed_fhe_int1024_safe_serialize(const struct CompressedFheInt1024 *sself,
  ///                                           struct DynamicBuffer *result,
  ///                                           uint64_t serialized_size_limit);
  ///```
  @Override
  public DynamicBuffer serialize(){
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int1024_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

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
  /// int compressed_fhe_int1024_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  ///                                                        uint64_t serialized_size_limit,
  ///                                                        const struct ServerKey *server_key,
  ///                                                        struct CompressedFheInt1024 **result);
  ///```
  public static CompressedFheInt1024 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey){
    CompressedFheInt1024 deserialized = new CompressedFheInt1024();
    execute(() -> compressed_fhe_int1024_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /// ```c
  /// int compressed_fhe_int1024_try_encrypt_with_client_key_i1024(struct I1024 value,
  ///                                                              const struct ClientKey *client_key,
  ///                                                              struct CompressedFheInt1024 **result);
  ///```
  public static CompressedFheInt1024 encrypt(I1024 clearValue, ClientKey clientKey) {
    CompressedFheInt1024 encrypted = new CompressedFheInt1024();
    execute(() -> compressed_fhe_int1024_try_encrypt_with_client_key_i1024(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /// ```c
  /// int compressed_fhe_int1024_clone(const struct CompressedFheInt1024 *sself,
  ///                                  struct CompressedFheInt1024 **result);
/// ```
@Override
@SuppressWarnings("MethodDoesntCallSuperMethod")
public CompressedFheInt1024 clone(){
    CompressedFheInt1024 cloned = new CompressedFheInt1024();
    execute(() -> compressed_fhe_int1024_clone(getValue(), cloned.getAddress()));
    return cloned;

}
}
