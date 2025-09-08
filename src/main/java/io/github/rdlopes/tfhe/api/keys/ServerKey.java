package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.server_key_safe_serialize;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.set_server_key;

public class ServerKey extends NativeValue implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(ServerKey.class);

  public ServerKey() {
    logger.trace("init");
    super(TfheHeader::server_key_destroy);
  }

  public static ServerKey deserialize(byte[] ignoredBuffer) {
    logger.trace("deserialize - ignoredBuffer: {}", ignoredBuffer);
    throw new UnsupportedOperationException("Server keys cannot be deserialized");
  }

  public byte[] serialize() {
    logger.trace("serialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> server_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  }

  public void setAsKey() {
    logger.trace("setAsKey");
    execute(() -> set_server_key(getValue()));
  }

}
