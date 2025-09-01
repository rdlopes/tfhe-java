package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint232 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint232() {
    super(CompressedFheUint232.class, TfheWrapper::compressed_fhe_uint232_destroy);
  }

  public static CompressedFheUint232 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint232 compressedFheUint232 = new CompressedFheUint232();
    executeWithErrorHandling(() -> compressed_fhe_uint232_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint232.getAddress()));
    return compressedFheUint232;
  }

  public static CompressedFheUint232 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint232 compressedFheUint232 = new CompressedFheUint232();
    executeWithErrorHandling(() -> compressed_fhe_uint232_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint232.getAddress()));
    return compressedFheUint232;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint232_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint232 decompress() {
    FheUint232 fheuint232 = new FheUint232();
    executeWithErrorHandling(() -> compressed_fhe_uint232_decompress(getValue(), fheuint232.getAddress()));
    return fheuint232;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint232 clone() {
    CompressedFheUint232 compressedFheUint232 = new CompressedFheUint232();
    executeWithErrorHandling(() -> compressed_fhe_uint232_clone(getValue(), compressedFheUint232.getAddress()));
    return compressedFheUint232;
  }
}