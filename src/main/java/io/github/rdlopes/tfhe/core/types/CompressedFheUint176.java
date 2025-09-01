package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint176 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint176() {
    super(CompressedFheUint176.class, TfheWrapper::compressed_fhe_uint176_destroy);
  }

  public static CompressedFheUint176 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint176 compressedFheUint176 = new CompressedFheUint176();
    executeWithErrorHandling(() -> compressed_fhe_uint176_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint176.getAddress()));
    return compressedFheUint176;
  }

  public static CompressedFheUint176 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint176 compressedFheUint176 = new CompressedFheUint176();
    executeWithErrorHandling(() -> compressed_fhe_uint176_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint176.getAddress()));
    return compressedFheUint176;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint176_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint176 decompress() {
    FheUint176 fheuint176 = new FheUint176();
    executeWithErrorHandling(() -> compressed_fhe_uint176_decompress(getValue(), fheuint176.getAddress()));
    return fheuint176;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint176 clone() {
    CompressedFheUint176 compressedFheUint176 = new CompressedFheUint176();
    executeWithErrorHandling(() -> compressed_fhe_uint176_clone(getValue(), compressedFheUint176.getAddress()));
    return compressedFheUint176;
  }
}