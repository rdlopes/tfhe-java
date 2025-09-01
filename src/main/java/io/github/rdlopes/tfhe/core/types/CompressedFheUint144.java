package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint144 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint144() {
    super(CompressedFheUint144.class, TfheWrapper::compressed_fhe_uint144_destroy);
  }

  public static CompressedFheUint144 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint144 compressedFheUint144 = new CompressedFheUint144();
    executeWithErrorHandling(() -> compressed_fhe_uint144_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint144.getAddress()));
    return compressedFheUint144;
  }

  public static CompressedFheUint144 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint144 compressedFheUint144 = new CompressedFheUint144();
    executeWithErrorHandling(() -> compressed_fhe_uint144_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint144.getAddress()));
    return compressedFheUint144;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint144_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint144 decompress() {
    FheUint144 fheuint144 = new FheUint144();
    executeWithErrorHandling(() -> compressed_fhe_uint144_decompress(getValue(), fheuint144.getAddress()));
    return fheuint144;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint144 clone() {
    CompressedFheUint144 compressedFheUint144 = new CompressedFheUint144();
    executeWithErrorHandling(() -> compressed_fhe_uint144_clone(getValue(), compressedFheUint144.getAddress()));
    return compressedFheUint144;
  }
}