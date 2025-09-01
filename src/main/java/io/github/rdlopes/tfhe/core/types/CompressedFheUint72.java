package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint72 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint72() {
    super(CompressedFheUint72.class, TfheWrapper::compressed_fhe_uint72_destroy);
  }

  public static CompressedFheUint72 encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint72 compressedFheUint72 = new CompressedFheUint72();
    executeWithErrorHandling(() -> compressed_fhe_uint72_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), compressedFheUint72.getAddress()));
    return compressedFheUint72;
  }

  public static CompressedFheUint72 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint72 compressedFheUint72 = new CompressedFheUint72();
    executeWithErrorHandling(() -> compressed_fhe_uint72_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint72.getAddress()));
    return compressedFheUint72;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint72_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint72 decompress() {
    FheUint72 fheuint72 = new FheUint72();
    executeWithErrorHandling(() -> compressed_fhe_uint72_decompress(getValue(), fheuint72.getAddress()));
    return fheuint72;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint72 clone() {
    CompressedFheUint72 compressedFheUint72 = new CompressedFheUint72();
    executeWithErrorHandling(() -> compressed_fhe_uint72_clone(getValue(), compressedFheUint72.getAddress()));
    return compressedFheUint72;
  }
}