package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint6 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint6() {
    super(CompressedFheUint6.class, TfheWrapper::compressed_fhe_uint6_destroy);
  }

  public static CompressedFheUint6 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    CompressedFheUint6 compressedFheUint6 = new CompressedFheUint6();
    executeWithErrorHandling(() -> compressed_fhe_uint6_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), compressedFheUint6.getAddress()));
    return compressedFheUint6;
  }

  public static CompressedFheUint6 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint6 compressedFheUint6 = new CompressedFheUint6();
    executeWithErrorHandling(() -> compressed_fhe_uint6_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint6.getAddress()));
    return compressedFheUint6;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint6_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint6 decompress() {
    FheUint6 fheuint6 = new FheUint6();
    executeWithErrorHandling(() -> compressed_fhe_uint6_decompress(getValue(), fheuint6.getAddress()));
    return fheuint6;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint6 clone() {
    CompressedFheUint6 compressedFheUint6 = new CompressedFheUint6();
    executeWithErrorHandling(() -> compressed_fhe_uint6_clone(getValue(), compressedFheUint6.getAddress()));
    return compressedFheUint6;
  }
}