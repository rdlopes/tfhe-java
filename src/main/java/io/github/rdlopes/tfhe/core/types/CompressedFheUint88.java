package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint88 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint88() {
    super(CompressedFheUint88.class, TfheWrapper::compressed_fhe_uint88_destroy);
  }

  public static CompressedFheUint88 encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint88 compressedFheUint88 = new CompressedFheUint88();
    executeWithErrorHandling(() -> compressed_fhe_uint88_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), compressedFheUint88.getAddress()));
    return compressedFheUint88;
  }

  public static CompressedFheUint88 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint88 compressedFheUint88 = new CompressedFheUint88();
    executeWithErrorHandling(() -> compressed_fhe_uint88_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint88.getAddress()));
    return compressedFheUint88;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint88_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint88 decompress() {
    FheUint88 fheuint88 = new FheUint88();
    executeWithErrorHandling(() -> compressed_fhe_uint88_decompress(getValue(), fheuint88.getAddress()));
    return fheuint88;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint88 clone() {
    CompressedFheUint88 compressedFheUint88 = new CompressedFheUint88();
    executeWithErrorHandling(() -> compressed_fhe_uint88_clone(getValue(), compressedFheUint88.getAddress()));
    return compressedFheUint88;
  }
}