package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint248 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint248() {
    super(CompressedFheUint248.class, TfheWrapper::compressed_fhe_uint248_destroy);
  }

  public static CompressedFheUint248 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint248 compressedFheUint248 = new CompressedFheUint248();
    executeWithErrorHandling(() -> compressed_fhe_uint248_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint248.getAddress()));
    return compressedFheUint248;
  }

  public static CompressedFheUint248 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint248 compressedFheUint248 = new CompressedFheUint248();
    executeWithErrorHandling(() -> compressed_fhe_uint248_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint248.getAddress()));
    return compressedFheUint248;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint248_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint248 decompress() {
    FheUint248 fheuint248 = new FheUint248();
    executeWithErrorHandling(() -> compressed_fhe_uint248_decompress(getValue(), fheuint248.getAddress()));
    return fheuint248;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint248 clone() {
    CompressedFheUint248 compressedFheUint248 = new CompressedFheUint248();
    executeWithErrorHandling(() -> compressed_fhe_uint248_clone(getValue(), compressedFheUint248.getAddress()));
    return compressedFheUint248;
  }
}