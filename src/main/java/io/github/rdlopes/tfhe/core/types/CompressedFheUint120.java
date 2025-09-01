package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint120 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint120() {
    super(CompressedFheUint120.class, TfheWrapper::compressed_fhe_uint120_destroy);
  }

  public static CompressedFheUint120 encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint120 compressedFheUint120 = new CompressedFheUint120();
    executeWithErrorHandling(() -> compressed_fhe_uint120_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), compressedFheUint120.getAddress()));
    return compressedFheUint120;
  }

  public static CompressedFheUint120 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint120 compressedFheUint120 = new CompressedFheUint120();
    executeWithErrorHandling(() -> compressed_fhe_uint120_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint120.getAddress()));
    return compressedFheUint120;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint120_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint120 decompress() {
    FheUint120 fheuint120 = new FheUint120();
    executeWithErrorHandling(() -> compressed_fhe_uint120_decompress(getValue(), fheuint120.getAddress()));
    return fheuint120;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint120 clone() {
    CompressedFheUint120 compressedFheUint120 = new CompressedFheUint120();
    executeWithErrorHandling(() -> compressed_fhe_uint120_clone(getValue(), compressedFheUint120.getAddress()));
    return compressedFheUint120;
  }
}