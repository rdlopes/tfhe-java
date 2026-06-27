package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.CompressedFheKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.NativePointer;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.api.serde.DynamicBuffer.MAX_SERIALIZATION_SIZE;
import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.*;

public class CompressedCompactPublicKey extends NativePointer implements CompressedFheKey<CompactPublicKey> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedCompactPublicKey.class);

  CompressedCompactPublicKey() {
    logger.trace("init");

    super(TfheHeader::compressed_compact_public_key_destroy);
  }

  public CompressedCompactPublicKey(ClientKey clientKey) {
    logger.trace("init");

    this();
    execute(() -> compressed_compact_public_key_new(clientKey.getValue(), getAddress()));
  }

  public static CompressedCompactPublicKey deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize - dynamicBuffer: {}", dynamicBuffer);

    CompressedCompactPublicKey deserialized = new CompressedCompactPublicKey();
    execute(() -> compressed_compact_public_key_safe_deserialize(dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  @Override
  public CompactPublicKey decompress() {
    logger.trace("decompress");

    CompactPublicKey decompressed = new CompactPublicKey();
    execute(() -> compressed_compact_public_key_decompress(getValue(), decompressed.getAddress()));
    return decompressed;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");

    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_compact_public_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), MAX_SERIALIZATION_SIZE));

    return dynamicBuffer;
  }
}
