package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint80 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint80() {
    super(CompressedFheUint80.class, TfheWrapper::compressed_fhe_uint80_destroy);
  }

  public static CompressedFheUint80 encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint80 compressedFheUint80 = new CompressedFheUint80();
    executeWithErrorHandling(() -> compressed_fhe_uint80_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), compressedFheUint80.getAddress()));
    return compressedFheUint80;
  }

  public static CompressedFheUint80 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint80 compressedFheUint80 = new CompressedFheUint80();
    executeWithErrorHandling(() -> compressed_fhe_uint80_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint80.getAddress()));
    return compressedFheUint80;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint80_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint80 decompress() {
    FheUint80 fheuint80 = new FheUint80();
    executeWithErrorHandling(() -> compressed_fhe_uint80_decompress(getValue(), fheuint80.getAddress()));
    return fheuint80;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint80 clone() {
    CompressedFheUint80 compressedFheUint80 = new CompressedFheUint80();
    executeWithErrorHandling(() -> compressed_fhe_uint80_clone(getValue(), compressedFheUint80.getAddress()));
    return compressedFheUint80;
  }
}