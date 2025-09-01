package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint184 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint184() {
    super(CompressedFheUint184.class, TfheWrapper::compressed_fhe_uint184_destroy);
  }

  public static CompressedFheUint184 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint184 compressedFheUint184 = new CompressedFheUint184();
    executeWithErrorHandling(() -> compressed_fhe_uint184_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint184.getAddress()));
    return compressedFheUint184;
  }

  public static CompressedFheUint184 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint184 compressedFheUint184 = new CompressedFheUint184();
    executeWithErrorHandling(() -> compressed_fhe_uint184_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint184.getAddress()));
    return compressedFheUint184;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint184_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint184 decompress() {
    FheUint184 fheuint184 = new FheUint184();
    executeWithErrorHandling(() -> compressed_fhe_uint184_decompress(getValue(), fheuint184.getAddress()));
    return fheuint184;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint184 clone() {
    CompressedFheUint184 compressedFheUint184 = new CompressedFheUint184();
    executeWithErrorHandling(() -> compressed_fhe_uint184_clone(getValue(), compressedFheUint184.getAddress()));
    return compressedFheUint184;
  }
}