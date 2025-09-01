package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint14 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint14() {
    super(CompressedFheUint14.class, TfheWrapper::compressed_fhe_uint14_destroy);
  }

  public static CompressedFheUint14 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    CompressedFheUint14 compressedFheUint14 = new CompressedFheUint14();
    executeWithErrorHandling(() -> compressed_fhe_uint14_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), compressedFheUint14.getAddress()));
    return compressedFheUint14;
  }

  public static CompressedFheUint14 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint14 compressedFheUint14 = new CompressedFheUint14();
    executeWithErrorHandling(() -> compressed_fhe_uint14_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint14.getAddress()));
    return compressedFheUint14;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint14_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint14 decompress() {
    FheUint14 fheuint14 = new FheUint14();
    executeWithErrorHandling(() -> compressed_fhe_uint14_decompress(getValue(), fheuint14.getAddress()));
    return fheuint14;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint14 clone() {
    CompressedFheUint14 compressedFheUint14 = new CompressedFheUint14();
    executeWithErrorHandling(() -> compressed_fhe_uint14_clone(getValue(), compressedFheUint14.getAddress()));
    return compressedFheUint14;
  }
}