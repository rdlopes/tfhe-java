package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.FheKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class PublicKey extends NativePointer implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(PublicKey.class);

  PublicKey() {
    logger.trace("init");
    super(TfheHeader::public_key_destroy);
  }

  public PublicKey(ClientKey clientKey) {
    this();
    execute(() -> public_key_new(clientKey.getValue(), getAddress()));
  }

  public static PublicKey deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize - dynamicBuffer: {}", dynamicBuffer);

    PublicKey deserialized = new PublicKey();
    execute(() -> public_key_safe_deserialize(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> public_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    return dynamicBuffer;
  }
}
