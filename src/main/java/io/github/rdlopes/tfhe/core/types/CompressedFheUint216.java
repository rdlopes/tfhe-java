package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint216 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint216() {
    super(CompressedFheUint216.class, TfheWrapper::compressed_fhe_uint216_destroy);
  }

  public static CompressedFheUint216 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint216 compressedFheUint216 = new CompressedFheUint216();
    executeWithErrorHandling(() -> compressed_fhe_uint216_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint216.getAddress()));
    return compressedFheUint216;
  }

  public static CompressedFheUint216 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint216 compressedFheUint216 = new CompressedFheUint216();
    executeWithErrorHandling(() -> compressed_fhe_uint216_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint216.getAddress()));
    return compressedFheUint216;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint216_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint216 decompress() {
    FheUint216 fheuint216 = new FheUint216();
    executeWithErrorHandling(() -> compressed_fhe_uint216_decompress(getValue(), fheuint216.getAddress()));
    return fheuint216;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint216 clone() {
    CompressedFheUint216 compressedFheUint216 = new CompressedFheUint216();
    executeWithErrorHandling(() -> compressed_fhe_uint216_clone(getValue(), compressedFheUint216.getAddress()));
    return compressedFheUint216;
  }
}