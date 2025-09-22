package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_16.*;

public class CompressedFheCompactPublicKey extends NativePointer implements CompressedFheKey<CompactPublicKey> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheCompactPublicKey.class);

  CompressedFheCompactPublicKey() {
    logger.trace("init");

    super(TfheHeader::compressed_compact_public_key_destroy);
  }

  public CompressedFheCompactPublicKey(ClientKey clientKey) {
    logger.trace("init");

    this();
    execute(() -> compressed_compact_public_key_new(clientKey.getValue(), getAddress()));
  }

  public static CompressedFheCompactPublicKey deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize - dynamicBuffer: {}", dynamicBuffer);

    CompressedFheCompactPublicKey deserialized = new CompressedFheCompactPublicKey();
    execute(() -> compressed_compact_public_key_safe_deserialize(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, deserialized.getAddress()));
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
    execute(() -> compressed_compact_public_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;
  }
}
