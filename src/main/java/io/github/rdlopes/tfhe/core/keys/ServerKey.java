package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import static io.github.rdlopes.tfhe.core.serde.FheSerializable.BUFFER_MAX_SIZE;
import static io.github.rdlopes.tfhe.ffm.TfheWrapper.server_key_destroy;
import static io.github.rdlopes.tfhe.ffm.TfheWrapper.server_key_safe_serialize;

public class ServerKey extends AddressLayoutPointer {

  public DynamicBuffer serialize() {
    DynamicBuffer buffer = new DynamicBuffer();
    executeWithErrorHandling(() -> server_key_safe_serialize(getValue(), buffer.getAddress(), BUFFER_MAX_SIZE));
    return buffer;
  }

  public void destroy() {
    executeWithErrorHandling(() -> server_key_destroy(getValue()));
  }
}
