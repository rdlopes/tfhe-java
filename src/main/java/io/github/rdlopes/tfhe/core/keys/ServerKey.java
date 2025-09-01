package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.server_key_safe_serialize;
import static io.github.rdlopes.tfhe.ffm.TfheWrapper.set_server_key;

public class ServerKey extends AddressLayoutPointer {

  public ServerKey() {
    super(ServerKey.class, TfheWrapper::server_key_destroy);
  }

  public static ServerKey deserialize(DynamicBufferView ignoredBufferView) {
    throw new UnsupportedOperationException("Server keys cannot be deserialized");
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> server_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public void setAsKey() {
    executeWithErrorHandling(() -> set_server_key(getValue()));
  }
}
