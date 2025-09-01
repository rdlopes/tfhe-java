package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.client_key_safe_deserialize;
import static io.github.rdlopes.tfhe.ffm.TfheWrapper.client_key_safe_serialize;

public class ClientKey extends AddressLayoutPointer {

  public ClientKey() {
    super(ClientKey.class, TfheWrapper::client_key_destroy);
  }

  public static ClientKey deserialize(DynamicBufferView bufferView) {
    ClientKey clientKey = new ClientKey();
    executeWithErrorHandling(() -> client_key_safe_deserialize(bufferView.getAddress(), BUFFER_MAX_SIZE, clientKey.getAddress()));
    return clientKey;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> client_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

}
