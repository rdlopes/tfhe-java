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
public class CompressedFheUint4 extends NativePointer
implements CompressedFheType<Byte, FheUint4, CompressedFheUint4> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint4.class);
// @formatter:on

  /**
   {@snippet lang = "c":
    *
    *ptr can be null (no-op in that case)
    *
     int compressed_fhe_uint4_destroy(struct CompressedFheUint4 *ptr);
     }
   */
  CompressedFheUint4() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint4_destroy);
  }

  /**
   {@snippet lang = "c":
     int compressed_fhe_uint4_decompress(const struct CompressedFheUint4 *sself,
     struct FheUint4 **result);
     }
   */
  @Override
  public FheUint4 decompress() {
    FheUint4 decompressed = new FheUint4();
    execute(() -> compressed_fhe_uint4_decompress(getValue(), decompressed.getAddress()));
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
int compressed_fhe_uint4_safe_serialize(const struct CompressedFheUint4 *sself,
  struct DynamicBuffer *result,
  uint64_t serialized_size_limit);
  }
 */
@Override
public DynamicBuffer serialize() {
  DynamicBuffer dynamicBuffer = new DynamicBuffer();
  execute(() -> compressed_fhe_uint4_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

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
int compressed_fhe_uint4_safe_deserialize_conformant(struct DynamicBufferView buffer_view,
  uint64_t serialized_size_limit,
  const struct ServerKey *server_key,
  struct CompressedFheUint4 **result);
  }
   */
  public static CompressedFheUint4 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint4 deserialized = new CompressedFheUint4();
    execute(() -> compressed_fhe_uint4_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

}/**
{@snippet lang = "c":
  int compressed_fhe_uint4_try_encrypt_with_client_key_u8(uint8_t value,
  const struct ClientKey *client_key,
  struct CompressedFheUint4 **result);
  }
   */
  public static CompressedFheUint4 encrypt(Byte clearValue, ClientKey clientKey) {
    CompressedFheUint4 encrypted = new CompressedFheUint4();
      execute(() -> compressed_fhe_uint4_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }
/**
{@snippet lang = "c":
  int compressed_fhe_uint4_clone(const struct CompressedFheUint4 *sself,
  struct CompressedFheUint4 **result);
  }
 */
@Override
@SuppressWarnings("MethodDoesntCallSuperMethod")
public CompressedFheUint4 clone() {
  CompressedFheUint4 cloned = new CompressedFheUint4();
    execute(() -> compressed_fhe_uint4_clone(getValue(), cloned.getAddress()));
    return cloned;

}}
