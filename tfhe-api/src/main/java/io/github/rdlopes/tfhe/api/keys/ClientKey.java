package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.FheKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.NativePointer;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.api.serde.DynamicBuffer.MAX_SERIALIZATION_SIZE;
import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.client_key_safe_deserialize;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.client_key_safe_serialize;

public class ClientKey extends NativePointer implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(ClientKey.class);

  ClientKey() {
    logger.trace("init");

    super(TfheHeader::client_key_destroy);
  }

  public static ClientKey deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize - dynamicBuffer: {}", dynamicBuffer);

    ClientKey deserialized = new ClientKey();
    execute(() -> client_key_safe_deserialize(dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");

    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> client_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE));

    return dynamicBuffer;
  }
}
