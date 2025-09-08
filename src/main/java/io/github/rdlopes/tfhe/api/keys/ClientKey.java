package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.client_key_safe_deserialize;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.client_key_safe_serialize;

public class ClientKey extends NativeValue implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(ClientKey.class);

  public ClientKey() {
    logger.trace("init");
    super(TfheHeader::client_key_destroy);
  }

  public static ClientKey deserialize(byte[] buffer) {
    logger.trace("deserialize - buffer: {}", buffer);
    DynamicBufferView bufferView = DynamicBufferView.fromByteArray(buffer);
    ClientKey clientKey = new ClientKey();
    execute(() -> client_key_safe_deserialize(bufferView.getAddress(), BUFFER_MAX_SIZE, clientKey.getAddress()));
    return clientKey;
  }

  public byte[] serialize() {
    logger.trace("serialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> client_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  }

}
