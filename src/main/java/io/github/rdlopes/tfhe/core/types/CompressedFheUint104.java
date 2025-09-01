package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint104 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint104() {
    super(CompressedFheUint104.class, TfheWrapper::compressed_fhe_uint104_destroy);
  }

  public static CompressedFheUint104 encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint104 compressedFheUint104 = new CompressedFheUint104();
    executeWithErrorHandling(() -> compressed_fhe_uint104_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), compressedFheUint104.getAddress()));
    return compressedFheUint104;
  }

  public static CompressedFheUint104 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint104 compressedFheUint104 = new CompressedFheUint104();
    executeWithErrorHandling(() -> compressed_fhe_uint104_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint104.getAddress()));
    return compressedFheUint104;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint104_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint104 decompress() {
    FheUint104 fheuint104 = new FheUint104();
    executeWithErrorHandling(() -> compressed_fhe_uint104_decompress(getValue(), fheuint104.getAddress()));
    return fheuint104;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint104 clone() {
    CompressedFheUint104 compressedFheUint104 = new CompressedFheUint104();
    executeWithErrorHandling(() -> compressed_fhe_uint104_clone(getValue(), compressedFheUint104.getAddress()));
    return compressedFheUint104;
  }
}