package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint12 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint12() {
    super(CompressedFheUint12.class, TfheWrapper::compressed_fhe_uint12_destroy);
  }

  public static CompressedFheUint12 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    CompressedFheUint12 compressedFheUint12 = new CompressedFheUint12();
    executeWithErrorHandling(() -> compressed_fhe_uint12_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), compressedFheUint12.getAddress()));
    return compressedFheUint12;
  }

  public static CompressedFheUint12 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint12 compressedFheUint12 = new CompressedFheUint12();
    executeWithErrorHandling(() -> compressed_fhe_uint12_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint12.getAddress()));
    return compressedFheUint12;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint12_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint12 decompress() {
    FheUint12 fheuint12 = new FheUint12();
    executeWithErrorHandling(() -> compressed_fhe_uint12_decompress(getValue(), fheuint12.getAddress()));
    return fheuint12;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint12 clone() {
    CompressedFheUint12 compressedFheUint12 = new CompressedFheUint12();
    executeWithErrorHandling(() -> compressed_fhe_uint12_clone(getValue(), compressedFheUint12.getAddress()));
    return compressedFheUint12;
  }
}