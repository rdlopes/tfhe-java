package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class ServerKey extends AddressLayoutPointer {

  public ServerKey() {
    super(address -> server_key_destroy(address.get(C_POINTER, 0)));
  }

  public static ServerKey deserialize(DynamicBufferView bufferView) {
    throw new UnsupportedOperationException("Server keys cannot be deserialized");
  }

  public DynamicBufferView serialize() {
    DynamicBuffer buffer = new DynamicBuffer();
    executeWithErrorHandling(() -> server_key_safe_serialize(getValue(), buffer.getAddress(), BUFFER_MAX_SIZE));
    return buffer.view();
  }

  public void setAsKey() {
    executeWithErrorHandling(() -> set_server_key(getValue()));
  }
}
