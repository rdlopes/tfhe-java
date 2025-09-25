package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.FheKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.*;

public class CompactPublicKey extends NativePointer implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(CompactPublicKey.class);

  CompactPublicKey() {
    logger.trace("init");
    super(TfheHeader::compact_public_key_destroy);
  }

  public CompactPublicKey(ClientKey clientKey) {
    logger.trace("init");
    this();
    execute(() -> compact_public_key_new(clientKey.getValue(), getAddress()));
  }

  public static CompactPublicKey deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize - dynamicBuffer: {}", dynamicBuffer);

    CompactPublicKey deserialized = new CompactPublicKey();
    execute(() -> compact_public_key_safe_deserialize(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");

    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compact_public_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;
  }
}
