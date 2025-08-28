package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class ServerKey extends AddressLayoutPointer {

  public static ServerKey deserialize(DynamicBufferView bufferView) {
    ServerKey serverKey = new ServerKey();
    executeWithErrorHandling(() -> server_key_deserialize(bufferView.getAddress(), serverKey.getAddress()));
    return serverKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer buffer = new DynamicBuffer();
    executeWithErrorHandling(() -> server_key_serialize(getValue(), buffer.getAddress()));
    return buffer;
  }

  public DynamicBuffer safeSerialize() {
    DynamicBuffer buffer = new DynamicBuffer();
    executeWithErrorHandling(() -> server_key_safe_serialize(getValue(), buffer.getAddress(), SERDE_MAX_SIZE));
    return buffer;
  }

  public void destroy() {
    executeWithErrorHandling(() -> server_key_destroy(getValue()));
  }
}
