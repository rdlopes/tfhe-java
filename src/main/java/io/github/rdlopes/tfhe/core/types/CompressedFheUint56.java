package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint56 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint56() {
    super(CompressedFheUint56.class, TfheWrapper::compressed_fhe_uint56_destroy);
  }

  public static CompressedFheUint56 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    CompressedFheUint56 compressedFheUint56 = new CompressedFheUint56();
    executeWithErrorHandling(() -> compressed_fhe_uint56_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), compressedFheUint56.getAddress()));
    return compressedFheUint56;
  }

  public static CompressedFheUint56 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint56 compressedFheUint56 = new CompressedFheUint56();
    executeWithErrorHandling(() -> compressed_fhe_uint56_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint56.getAddress()));
    return compressedFheUint56;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint56_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint56 decompress() {
    FheUint56 fheuint56 = new FheUint56();
    executeWithErrorHandling(() -> compressed_fhe_uint56_decompress(getValue(), fheuint56.getAddress()));
    return fheuint56;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint56 clone() {
    CompressedFheUint56 compressedFheUint56 = new CompressedFheUint56();
    executeWithErrorHandling(() -> compressed_fhe_uint56_clone(getValue(), compressedFheUint56.getAddress()));
    return compressedFheUint56;
  }
}