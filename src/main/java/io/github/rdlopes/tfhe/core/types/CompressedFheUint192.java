package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint192 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint192() {
    super(CompressedFheUint192.class, TfheWrapper::compressed_fhe_uint192_destroy);
  }

  public static CompressedFheUint192 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint192 compressedFheUint192 = new CompressedFheUint192();
    executeWithErrorHandling(() -> compressed_fhe_uint192_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint192.getAddress()));
    return compressedFheUint192;
  }

  public static CompressedFheUint192 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint192 compressedFheUint192 = new CompressedFheUint192();
    executeWithErrorHandling(() -> compressed_fhe_uint192_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint192.getAddress()));
    return compressedFheUint192;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint192_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint192 decompress() {
    FheUint192 fheuint192 = new FheUint192();
    executeWithErrorHandling(() -> compressed_fhe_uint192_decompress(getValue(), fheuint192.getAddress()));
    return fheuint192;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint192 clone() {
    CompressedFheUint192 compressedFheUint192 = new CompressedFheUint192();
    executeWithErrorHandling(() -> compressed_fhe_uint192_clone(getValue(), compressedFheUint192.getAddress()));
    return compressedFheUint192;
  }
}