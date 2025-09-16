package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompressedServerKey extends NativeValue implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(CompressedServerKey.class);

  /**
   {@snippet lang = "c":
     &#47;&#42;&#42;
    *ptr can be null (no-op in that case)
     &#42;&#47;
     int compressed_server_key_destroy(struct CompressedServerKey *ptr);
     }
   */
  CompressedServerKey() {
    logger.trace("init");
    super(TfheHeader::compressed_server_key_destroy);
  }

  public static CompressedServerKey createFromClientKey(ClientKey clientKey) {
    logger.trace("createFromClientKey - clientKey: {}", clientKey);
    CompressedServerKey key = new CompressedServerKey();
    execute(() -> compressed_server_key_new(clientKey.getValue(), key.getAddress()));
    return key;
  }

  public static CompressedServerKey deserialize(byte[] buffer) {
    logger.trace("deserialize - buffer: {}", buffer);
    DynamicBufferView bufferView = DynamicBufferView.fromByteArray(buffer);
    CompressedServerKey key = new CompressedServerKey();
    execute(() -> compressed_server_key_safe_deserialize(bufferView.getAddress(), BUFFER_MAX_SIZE, key.getAddress()));
    return key;
  }

  /**
   {@snippet lang = "c":
     &#47;&#42;&#42;
    * Serializes safely.
    *
    * This function adds versioning information to the serialized buffer, meaning that it will keep compatibility with future
    * versions of TFHE-rs.
    *
    * - `serialized_size_limit`: size limit (in number of byte) of the serialized object
    *    (to avoid out of memory attacks)
     &#42;&#47;
     int compressed_server_key_safe_serialize(const struct CompressedServerKey *sself,
     struct DynamicBuffer *result,
     uint64_t serialized_size_limit);
     }
   */
  public byte[] serialize() {
    logger.trace("serialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> compressed_server_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  }

  /**
   {@snippet lang = "c":
     int compressed_server_key_serialize(const struct CompressedServerKey *sself,
     struct DynamicBuffer *result);
     }
   */
  public byte[] unsafeSerialize() {
    logger.trace("unsafeSerialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> compressed_server_key_serialize(getValue(), dynamicBuffer.getAddress()));
      return dynamicBuffer.toByteArray();
    }
  }

  /**
   {@snippet lang = "c":
     &#47;&#42;&#42;
    * Decompresses the CompressedServerKey to a ServerKey that lives on CPU
     &#42;&#47;
     int compressed_server_key_decompress(const struct CompressedServerKey *compressed_server_key,
     struct ServerKey **result_server_key);
     }
   */
  public ServerKey decompress() {
    logger.trace("decompress");
    ServerKey decompressed = new ServerKey();
    execute(() -> compressed_server_key_decompress(getValue(), decompressed.getAddress()));
    return decompressed;
  }

}
