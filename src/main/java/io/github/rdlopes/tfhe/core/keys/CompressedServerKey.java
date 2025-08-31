package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedServerKey extends AddressLayoutPointer {

  public CompressedServerKey() {
    super(CompressedServerKey.class, TfheWrapper::compressed_server_key_destroy);
  }

  public static CompressedServerKey newWith(ClientKey clientKey) {
    CompressedServerKey compressedServerKey = new CompressedServerKey();
    executeWithErrorHandling(() -> compressed_server_key_new(clientKey.getValue(), compressedServerKey.getAddress()));
    return compressedServerKey;
  }

  public static CompressedServerKey deserialize(DynamicBufferView bufferView) {
    CompressedServerKey compressedServerKey = new CompressedServerKey();
    executeWithErrorHandling(() -> compressed_server_key_safe_deserialize(bufferView.getAddress(), BUFFER_MAX_SIZE, compressedServerKey.getAddress()));
    return compressedServerKey;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_server_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    return dynamicBuffer.view();
  }

  public ServerKey decompress() {
    ServerKey serverKey = new ServerKey();
    executeWithErrorHandling(() -> compressed_server_key_decompress(getValue(), serverKey.getAddress()));
    return serverKey;
  }
}
