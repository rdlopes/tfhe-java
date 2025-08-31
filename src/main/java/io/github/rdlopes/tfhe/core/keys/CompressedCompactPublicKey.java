package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedCompactPublicKey extends AddressLayoutPointer {

  public CompressedCompactPublicKey() {
    super(CompressedCompactPublicKey.class, TfheWrapper::compressed_compact_public_key_destroy);
  }

  public static CompressedCompactPublicKey newWith(ClientKey clientKey) {
    CompressedCompactPublicKey compressedCompactPublicKey = new CompressedCompactPublicKey();
    executeWithErrorHandling(() -> compressed_compact_public_key_new(clientKey.getValue(), compressedCompactPublicKey.getAddress()));
    return compressedCompactPublicKey;
  }

  public static CompressedCompactPublicKey deserialize(DynamicBufferView bufferView) {
    CompressedCompactPublicKey compressedCompactPublicKey = new CompressedCompactPublicKey();
    executeWithErrorHandling(() -> compressed_compact_public_key_safe_deserialize(bufferView.getAddress(), BUFFER_MAX_SIZE, compressedCompactPublicKey.getAddress()));
    return compressedCompactPublicKey;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_compact_public_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.cleanNativeResources();
    return dynamicBufferView;
  }
}
