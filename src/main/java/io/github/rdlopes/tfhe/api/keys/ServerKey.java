package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class ServerKey extends NativeValue implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(ServerKey.class);

  /**
   {@snippet lang = "c":
     &#47;&#42;&#42;
    *ptr can be null (no-op in that case)
     &#42;&#47;
     int server_key_destroy(struct ServerKey *ptr);
     }
   */
  ServerKey() {
    logger.trace("init");
    super(TfheHeader::server_key_destroy);
  }

  public static ServerKey deserialize(byte[] ignoredBuffer) {
    logger.trace("deserialize - ignoredBuffer: {}", ignoredBuffer);
    throw new UnsupportedOperationException("ServerKey cannot be deserialized");
  }

  public static void unset() {
    logger.trace("unset");
    execute(TfheHeader::unset_server_key);
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
     int server_key_safe_serialize(const struct ServerKey *sself,
     struct DynamicBuffer *result,
     uint64_t serialized_size_limit);
     }
   */
  public byte[] serialize() {
    logger.trace("serialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> server_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  }

  /**
   {@snippet lang = "c":
     int server_key_serialize(const struct ServerKey *sself, struct DynamicBuffer *result);
     }
   */
  public byte[] unsafeSerialize() {
    logger.trace("unsafeSerialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> server_key_serialize(getValue(), dynamicBuffer.getAddress()));
      return dynamicBuffer.toByteArray();
    }
  }

  public void set() {
    logger.trace("set");
    execute(() -> set_server_key(getValue()));
  }

}
