package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader_15.*;

public class CompressedFheServerKey extends NativePointer implements CompressedFheKey<ServerKey> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheServerKey.class);

  CompressedFheServerKey() {
    logger.trace("init");

    super(TfheHeader::compressed_compact_public_key_destroy);
  }

  public CompressedFheServerKey(ClientKey clientKey) {
    logger.trace("init");

    this();
    execute(() -> compressed_server_key_new(clientKey.getValue(), getAddress()));
  }

  public static CompressedFheServerKey deserialize(DynamicBuffer dynamicBuffer) {
    logger.trace("deserialize - dynamicBuffer: {}", dynamicBuffer);

    CompressedFheServerKey deserialized = new CompressedFheServerKey();
    execute(() -> compressed_server_key_safe_deserialize(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, deserialized.getAddress()));
    return deserialized;
  }

  @Override
  public ServerKey decompress() {
    logger.trace("decompress");

    ServerKey decompressed = new ServerKey();
    execute(() -> compressed_server_key_decompress(getValue(), decompressed.getAddress()));
    return decompressed;
  }

  @Override
  public DynamicBuffer serialize() {
    logger.trace("serialize");

    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_server_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;
  }
}
