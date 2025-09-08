package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import io.github.rdlopes.tfhe.internal.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.internal.serde.DynamicBufferView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompressedServerKey extends NativeValue implements FheKey {
  private static final Logger logger = LoggerFactory.getLogger(CompressedServerKey.class);

  public CompressedServerKey() {
    logger.trace("init");
    super(TfheHeader::compressed_server_key_destroy);
  }

  public static CompressedServerKey newWith(ClientKey clientKey) {
    logger.trace("newWith - clientKey: {}", clientKey);
    CompressedServerKey compressedServerKey = new CompressedServerKey();
    execute(() -> compressed_server_key_new(clientKey.getValue(), compressedServerKey.getAddress()));
    return compressedServerKey;
  }

  public static CompressedServerKey deserialize(byte[] buffer) {
    logger.trace("deserialize - buffer: {}", buffer);
    DynamicBufferView bufferView = DynamicBufferView.fromByteArray(buffer);
    CompressedServerKey compressedServerKey = new CompressedServerKey();
    execute(() -> compressed_server_key_safe_deserialize(bufferView.getAddress(), BUFFER_MAX_SIZE, compressedServerKey.getAddress()));
    return compressedServerKey;
  }

  public byte[] serialize() {
    logger.trace("serialize");
    try (DynamicBuffer dynamicBuffer = new DynamicBuffer()) {
      execute(() -> compressed_server_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
      return dynamicBuffer.toByteArray();
    }
  }

  public ServerKey decompress() {
    logger.trace("decompress");
    ServerKey serverKey = new ServerKey();
    execute(() -> compressed_server_key_decompress(getValue(), serverKey.getAddress()));
    return serverKey;
  }

}
