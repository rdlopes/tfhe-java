package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.serde.FheAbstractSerializable;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.server_key_destroy;
import static io.github.rdlopes.tfhe.ffm.TfheWrapper.server_key_safe_serialize;

public class ServerKey extends FheAbstractSerializable {

  public DynamicBuffer serialize() {
    DynamicBuffer buffer = new DynamicBuffer();
    executeWithErrorHandling(() -> server_key_safe_serialize(getValue(), buffer.getAddress(), BUFFER_MAX_SIZE));
    return buffer;
  }

  @Override
  public void deserialize(DynamicBufferView bufferView) {
    throw new UnsupportedOperationException("Server keys cannot be deserialized");
  }

  public void destroy() {
    executeWithErrorHandling(() -> server_key_destroy(getValue()));
  }
}
