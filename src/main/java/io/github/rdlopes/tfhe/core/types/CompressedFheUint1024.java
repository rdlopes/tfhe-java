package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint1024 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint1024() {
    super(CompressedFheUint1024.class, TfheWrapper::compressed_fhe_uint1024_destroy);
  }

  public static CompressedFheUint1024 encryptWithClientKey(U1024 clearValue, ClientKey clientKey) {
    CompressedFheUint1024 compressedFheUint1024 = new CompressedFheUint1024();
    executeWithErrorHandling(() -> compressed_fhe_uint1024_try_encrypt_with_client_key_u1024(clearValue.getAddress(), clientKey.getValue(), compressedFheUint1024.getAddress()));
    return compressedFheUint1024;
  }

  public static CompressedFheUint1024 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint1024 compressedFheUint1024 = new CompressedFheUint1024();
    executeWithErrorHandling(() -> compressed_fhe_uint1024_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint1024.getAddress()));
    return compressedFheUint1024;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint1024_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint1024 decompress() {
    FheUint1024 fheuint1024 = new FheUint1024();
    executeWithErrorHandling(() -> compressed_fhe_uint1024_decompress(getValue(), fheuint1024.getAddress()));
    return fheuint1024;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint1024 clone() {
    CompressedFheUint1024 compressedFheUint1024 = new CompressedFheUint1024();
    executeWithErrorHandling(() -> compressed_fhe_uint1024_clone(getValue(), compressedFheUint1024.getAddress()));
    return compressedFheUint1024;
  }
}