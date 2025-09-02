package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint112 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint112() {
    super(CompressedFheUint112.class, TfheWrapper::compressed_fhe_uint112_destroy);
  }

  public static CompressedFheUint112 encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint112 compressed = new CompressedFheUint112();
    executeWithErrorHandling(() -> compressed_fhe_uint112_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint112 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint112 compressed = new CompressedFheUint112();
    executeWithErrorHandling(() -> compressed_fhe_uint112_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint112_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint112 decompress() {
    FheUint112 fhe = new FheUint112();
    executeWithErrorHandling(() -> compressed_fhe_uint112_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint112 clone() {
    CompressedFheUint112 cloned = new CompressedFheUint112();
    executeWithErrorHandling(() -> compressed_fhe_uint112_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
