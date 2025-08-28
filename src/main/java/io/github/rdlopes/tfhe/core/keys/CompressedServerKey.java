package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedServerKey extends AddressLayoutPointer {

  public static CompressedServerKey deserialize(DynamicBufferView bufferView) {
    CompressedServerKey compressedServerKey = new CompressedServerKey();
    executeWithErrorHandling(() -> compressed_server_key_deserialize(bufferView.getAddress(), compressedServerKey.getAddress()));
    return compressedServerKey;
  }

  public static CompressedServerKey safeDeserialize(DynamicBufferView bufferView) {
    CompressedServerKey compressedServerKey = new CompressedServerKey();
    executeWithErrorHandling(() -> compressed_server_key_safe_deserialize(bufferView.getAddress(), SERDE_MAX_SIZE, compressedServerKey.getAddress()));
    return compressedServerKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_server_key_serialize(getValue(), dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  public DynamicBuffer safeSerialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_server_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), SERDE_MAX_SIZE));
    return dynamicBuffer;
  }

  public ServerKey decompress() {
    ServerKey serverKey = new ServerKey();
    executeWithErrorHandling(() -> compressed_server_key_decompress(getValue(), serverKey.getAddress()));
    return serverKey;
  }

  public void destroy() {
    executeWithErrorHandling(() -> compressed_server_key_destroy(getValue()));
  }
}
