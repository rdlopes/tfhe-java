package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompressedCompactPublicKey extends NativeValue implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(CompressedCompactPublicKey.class);

  public CompressedCompactPublicKey() {
    logger.trace("init");
    super(TfheHeader::compressed_compact_public_key_destroy);
  }

  public static CompressedCompactPublicKey newWith(ClientKey clientKey) {
    logger.trace("newWith - clientKey: {}", clientKey);
    CompressedCompactPublicKey compressedCompactPublicKey = new CompressedCompactPublicKey();
    execute(() -> compressed_compact_public_key_new(clientKey.getValue(), compressedCompactPublicKey.getAddress()));
    return compressedCompactPublicKey;
  }

  public static CompressedCompactPublicKey deserialize(byte[] buffer) {
    logger.trace("deserialize - buffer: {}", buffer);
    DynamicBufferView bufferView = DynamicBufferView.fromByteArray(buffer);
    CompressedCompactPublicKey compressedCompactPublicKey = new CompressedCompactPublicKey();
    execute(() -> compressed_compact_public_key_safe_deserialize(bufferView.getAddress(), BUFFER_MAX_SIZE, compressedCompactPublicKey.getAddress()));
    return compressedCompactPublicKey;
  }

  public byte[] serialize() {
    logger.trace("serialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> compressed_compact_public_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  }

}
