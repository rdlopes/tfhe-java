package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class ClientKey extends AddressLayoutPointer {

  public static ClientKey deserialize(DynamicBufferView bufferView) {
    ClientKey clientKey = new ClientKey();
    executeWithErrorHandling(() -> client_key_deserialize(bufferView.getAddress(), clientKey.getAddress()));
    return clientKey;
  }

  public static ClientKey safeDeserialize(DynamicBufferView bufferView) {
    ClientKey clientKey = new ClientKey();
    executeWithErrorHandling(() -> client_key_safe_deserialize(bufferView.getAddress(), SERDE_MAX_SIZE, clientKey.getAddress()));
    return clientKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> client_key_serialize(getValue(), dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  public DynamicBuffer safeSerialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> client_key_safe_serialize(getValue(), dynamicBuffer.getAddress(), SERDE_MAX_SIZE));
    return dynamicBuffer;
  }

  public PublicKey generatePublicKey() {
    PublicKey publicKey = new PublicKey();
    executeWithErrorHandling(() -> public_key_new(getValue(), publicKey.getAddress()));
    return publicKey;
  }

  public CompressedCompactPublicKey generateCompactPublicKey() {
    CompressedCompactPublicKey compressedCompactPublicKey = new CompressedCompactPublicKey();
    executeWithErrorHandling(() -> compressed_compact_public_key_new(getValue(), compressedCompactPublicKey.getAddress()));
    return compressedCompactPublicKey;
  }

  public CompressedServerKey generateCompressedPublicKey() {
    CompressedServerKey compressedServerKey = new CompressedServerKey();
    executeWithErrorHandling(() -> compressed_server_key_new(getValue(), compressedServerKey.getAddress()));
    return compressedServerKey;
  }

  public void destroy() {
    executeWithErrorHandling(() -> client_key_destroy(getValue()));
  }
}
