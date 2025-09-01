package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint168 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint168() {
    super(CompressedFheUint168.class, TfheWrapper::compressed_fhe_uint168_destroy);
  }

  public static CompressedFheUint168 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint168 compressedFheUint168 = new CompressedFheUint168();
    executeWithErrorHandling(() -> compressed_fhe_uint168_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint168.getAddress()));
    return compressedFheUint168;
  }

  public static CompressedFheUint168 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint168 compressedFheUint168 = new CompressedFheUint168();
    executeWithErrorHandling(() -> compressed_fhe_uint168_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint168.getAddress()));
    return compressedFheUint168;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint168_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint168 decompress() {
    FheUint168 fheuint168 = new FheUint168();
    executeWithErrorHandling(() -> compressed_fhe_uint168_decompress(getValue(), fheuint168.getAddress()));
    return fheuint168;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint168 clone() {
    CompressedFheUint168 compressedFheUint168 = new CompressedFheUint168();
    executeWithErrorHandling(() -> compressed_fhe_uint168_clone(getValue(), compressedFheUint168.getAddress()));
    return compressedFheUint168;
  }
}