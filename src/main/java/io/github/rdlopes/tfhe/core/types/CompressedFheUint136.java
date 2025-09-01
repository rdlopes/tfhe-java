package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint136 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint136() {
    super(CompressedFheUint136.class, TfheWrapper::compressed_fhe_uint136_destroy);
  }

  public static CompressedFheUint136 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint136 compressedFheUint136 = new CompressedFheUint136();
    executeWithErrorHandling(() -> compressed_fhe_uint136_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint136.getAddress()));
    return compressedFheUint136;
  }

  public static CompressedFheUint136 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint136 compressedFheUint136 = new CompressedFheUint136();
    executeWithErrorHandling(() -> compressed_fhe_uint136_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint136.getAddress()));
    return compressedFheUint136;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint136_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint136 decompress() {
    FheUint136 fheuint136 = new FheUint136();
    executeWithErrorHandling(() -> compressed_fhe_uint136_decompress(getValue(), fheuint136.getAddress()));
    return fheuint136;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint136 clone() {
    CompressedFheUint136 compressedFheUint136 = new CompressedFheUint136();
    executeWithErrorHandling(() -> compressed_fhe_uint136_clone(getValue(), compressedFheUint136.getAddress()));
    return compressedFheUint136;
  }
}