package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.core.serde.FheAbstractSerializable;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class PublicKey extends FheAbstractSerializable {

  public void deserialize(DynamicBufferView bufferView) {
    executeWithErrorHandling(() -> public_key_safe_deserialize(bufferView.getAddress(), BUFFER_MAX_SIZE, getAddress()));
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> public_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    return dynamicBuffer;
  }

  public void destroy() {
    executeWithErrorHandling(() -> public_key_destroy(getValue()));
  }
}
