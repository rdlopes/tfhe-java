package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint240 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint240() {
    super(CompressedFheUint240.class, TfheWrapper::compressed_fhe_uint240_destroy);
  }

  public static CompressedFheUint240 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint240 compressedFheUint240 = new CompressedFheUint240();
    executeWithErrorHandling(() -> compressed_fhe_uint240_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint240.getAddress()));
    return compressedFheUint240;
  }

  public static CompressedFheUint240 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint240 compressedFheUint240 = new CompressedFheUint240();
    executeWithErrorHandling(() -> compressed_fhe_uint240_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint240.getAddress()));
    return compressedFheUint240;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint240_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint240 decompress() {
    FheUint240 fheuint240 = new FheUint240();
    executeWithErrorHandling(() -> compressed_fhe_uint240_decompress(getValue(), fheuint240.getAddress()));
    return fheuint240;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint240 clone() {
    CompressedFheUint240 compressedFheUint240 = new CompressedFheUint240();
    executeWithErrorHandling(() -> compressed_fhe_uint240_clone(getValue(), compressedFheUint240.getAddress()));
    return compressedFheUint240;
  }
}