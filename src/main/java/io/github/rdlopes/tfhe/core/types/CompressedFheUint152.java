package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint152 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint152() {
    super(CompressedFheUint152.class, TfheWrapper::compressed_fhe_uint152_destroy);
  }

  public static CompressedFheUint152 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint152 compressedFheUint152 = new CompressedFheUint152();
    executeWithErrorHandling(() -> compressed_fhe_uint152_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint152.getAddress()));
    return compressedFheUint152;
  }

  public static CompressedFheUint152 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint152 compressedFheUint152 = new CompressedFheUint152();
    executeWithErrorHandling(() -> compressed_fhe_uint152_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint152.getAddress()));
    return compressedFheUint152;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint152_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint152 decompress() {
    FheUint152 fheuint152 = new FheUint152();
    executeWithErrorHandling(() -> compressed_fhe_uint152_decompress(getValue(), fheuint152.getAddress()));
    return fheuint152;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint152 clone() {
    CompressedFheUint152 compressedFheUint152 = new CompressedFheUint152();
    executeWithErrorHandling(() -> compressed_fhe_uint152_clone(getValue(), compressedFheUint152.getAddress()));
    return compressedFheUint152;
  }
}