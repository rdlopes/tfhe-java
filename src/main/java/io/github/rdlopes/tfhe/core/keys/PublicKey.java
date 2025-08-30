package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import static io.github.rdlopes.tfhe.core.serde.FheSerializable.BUFFER_MAX_SIZE;
import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class PublicKey extends AddressLayoutPointer {

  public static PublicKey deserialize(DynamicBufferView bufferView) {
    PublicKey publicKey = new PublicKey();
    executeWithErrorHandling(() -> public_key_safe_deserialize(bufferView.getAddress(), BUFFER_MAX_SIZE, publicKey.getAddress()));
    return publicKey;
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
