package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint24 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint24() {
    super(CompressedFheUint24.class, TfheWrapper::compressed_fhe_uint24_destroy);
  }

  public static CompressedFheUint24 encryptWithClientKey(int clearValue, ClientKey clientKey) {
    CompressedFheUint24 compressedFheUint24 = new CompressedFheUint24();
    executeWithErrorHandling(() -> compressed_fhe_uint24_try_encrypt_with_client_key_u32(clearValue, clientKey.getValue(), compressedFheUint24.getAddress()));
    return compressedFheUint24;
  }

  public static CompressedFheUint24 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint24 compressedFheUint24 = new CompressedFheUint24();
    executeWithErrorHandling(() -> compressed_fhe_uint24_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint24.getAddress()));
    return compressedFheUint24;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint24_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint24 decompress() {
    FheUint24 fheuint24 = new FheUint24();
    executeWithErrorHandling(() -> compressed_fhe_uint24_decompress(getValue(), fheuint24.getAddress()));
    return fheuint24;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint24 clone() {
    CompressedFheUint24 compressedFheUint24 = new CompressedFheUint24();
    executeWithErrorHandling(() -> compressed_fhe_uint24_clone(getValue(), compressedFheUint24.getAddress()));
    return compressedFheUint24;
  }
}