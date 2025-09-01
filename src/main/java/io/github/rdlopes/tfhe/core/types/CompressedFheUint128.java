package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint128 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint128() {
    super(CompressedFheUint128.class, TfheWrapper::compressed_fhe_uint128_destroy);
  }

  public static CompressedFheUint128 encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint128 compressedFheUint128 = new CompressedFheUint128();
    executeWithErrorHandling(() -> compressed_fhe_uint128_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), compressedFheUint128.getAddress()));
    return compressedFheUint128;
  }

  public static CompressedFheUint128 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint128 compressedFheUint128 = new CompressedFheUint128();
    executeWithErrorHandling(() -> compressed_fhe_uint128_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint128.getAddress()));
    return compressedFheUint128;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint128_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint128 decompress() {
    FheUint128 fheuint128 = new FheUint128();
    executeWithErrorHandling(() -> compressed_fhe_uint128_decompress(getValue(), fheuint128.getAddress()));
    return fheuint128;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint128 clone() {
    CompressedFheUint128 compressedFheUint128 = new CompressedFheUint128();
    executeWithErrorHandling(() -> compressed_fhe_uint128_clone(getValue(), compressedFheUint128.getAddress()));
    return compressedFheUint128;
  }
}