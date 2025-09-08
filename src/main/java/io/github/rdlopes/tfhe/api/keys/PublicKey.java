package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class PublicKey extends NativeValue implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(PublicKey.class);

  public PublicKey() {
    logger.trace("init");
    super(TfheHeader::public_key_destroy);
  }

  public static PublicKey newWith(ClientKey clientKey) {
    logger.trace("newWith - clientKey: {}", clientKey);
    PublicKey publicKey = new PublicKey();
    execute(() -> public_key_new(clientKey.getValue(), publicKey.getAddress()));
    return publicKey;
  }

  public static PublicKey deserialize(byte[] buffer) {
    logger.trace("deserialize - buffer: {}", buffer);
    DynamicBufferView bufferView = DynamicBufferView.fromByteArray(buffer);
    PublicKey publicKey = new PublicKey();
    execute(() -> public_key_safe_deserialize(bufferView.getAddress(), BUFFER_MAX_SIZE, publicKey.getAddress()));
    return publicKey;
  }

  public byte[] serialize() {
    logger.trace("serialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> public_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  }

}
