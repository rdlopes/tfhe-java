package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint112 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint112() {
    super(CompressedFheUint112.class, TfheWrapper::compressed_fhe_uint112_destroy);
  }

  public static CompressedFheUint112 encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint112 compressedFheUint112 = new CompressedFheUint112();
    executeWithErrorHandling(() -> compressed_fhe_uint112_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), compressedFheUint112.getAddress()));
    return compressedFheUint112;
  }

  public static CompressedFheUint112 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint112 compressedFheUint112 = new CompressedFheUint112();
    executeWithErrorHandling(() -> compressed_fhe_uint112_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint112.getAddress()));
    return compressedFheUint112;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint112_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint112 decompress() {
    FheUint112 fheuint112 = new FheUint112();
    executeWithErrorHandling(() -> compressed_fhe_uint112_decompress(getValue(), fheuint112.getAddress()));
    return fheuint112;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint112 clone() {
    CompressedFheUint112 compressedFheUint112 = new CompressedFheUint112();
    executeWithErrorHandling(() -> compressed_fhe_uint112_clone(getValue(), compressedFheUint112.getAddress()));
    return compressedFheUint112;
  }
}