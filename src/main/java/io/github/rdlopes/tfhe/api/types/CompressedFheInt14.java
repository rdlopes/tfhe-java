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
public class CompressedFheInt14 extends NativePointer
implements CompressedFheType<Short, FheInt14, CompressedFheInt14> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt14.class);
// @formatter:on

  /**
   {@snippet lang = "c":
    *
    *ptr can be null (no-op in that case)
    *
     int compressed_fhe_int14_destroy(struct CompressedFheInt14 *ptr);
     }
   */
  CompressedFheInt14() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int14_destroy);
  }

  /**
   {@snippet lang = "c":
     int compressed_fhe_int14_decompress(const struct CompressedFheInt14 *sself,
     struct FheInt14 **result);
     }
   */
  @Override
  public FheInt14 decompress() {
    FheInt14 decompressed = new FheInt14();
    execute(() -> compressed_fhe_int14_decompress(getValue(), decompressed.getAddress()));
      return decompressed;

}  
/**
{@snippet lang = "c":
 *
 * Serializes safely.
 *
 * This function adds versioning information to the serialized buffer, meaning that it will keep compatibility with future
 * versions of TFHE-rs.
 *
 * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
 *    (to avoid out of memory attacks)
 *
int compressed_fhe_int14_safe_serialize(const struct CompressedFheInt14 *sself,
  struct DynamicBuffer *result,
  uint64_t serialized_size_limit);
  }
 */
@Override
public DynamicBuffer serialize() {
  DynamicBuffer dynamicBuffer = new DynamicBuffer();
  execute(() -> compressed_fhe_int14_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

  return dynamicBuffer;

}/**
{@snippet lang = "c":
 *
 * Deserializes safely, and checks that the resulting ciphertext
 * is in compliance with the shape of ciphertext that the `server_key` expects.
 *
 * This function can only deserialize types which have been serialized
 * by a `safe_serialize` function.
 *
 * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
 *    (to avoid out of memory attacks)
 * - `server_key`: ServerKey used in the conformance check
 * - `result`: pointer where resulting deserialized object needs to be stored.
 *    * cannot be NULL
 *    * (*result) will point the deserialized object on success, else NULL
 *
int compressed_fhe_int14_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  uint64_t serialized_size_limit,
  const struct ServerKey *server_key,
  struct CompressedFheInt14 **result);
  }
   */
  public static CompressedFheInt14 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt14 deserialized = new CompressedFheInt14();
    execute(() -> compressed_fhe_int14_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

}/**
{@snippet lang = "c":
  int compressed_fhe_int14_try_encrypt_with_client_key_i16(int16_t value,
  const struct ClientKey *client_key,
  struct CompressedFheInt14 **result);
  }
   */
  public static CompressedFheInt14 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheInt14 encrypted = new CompressedFheInt14();
      execute(() -> compressed_fhe_int14_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }
/**
{@snippet lang = "c":
  int compressed_fhe_int14_clone(const struct CompressedFheInt14 *sself,
  struct CompressedFheInt14 **result);
  }
 */
@Override
@SuppressWarnings("MethodDoesntCallSuperMethod")
public CompressedFheInt14 clone() {
  CompressedFheInt14 cloned = new CompressedFheInt14();
    execute(() -> compressed_fhe_int14_clone(getValue(), cloned.getAddress()));
    return cloned;

}}
