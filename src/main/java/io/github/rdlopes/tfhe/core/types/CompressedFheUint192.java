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
    CompressedFheUint192 compressed = new CompressedFheUint192();
    executeWithErrorHandling(() -> compressed_fhe_uint192_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint192 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint192 compressed = new CompressedFheUint192();
    executeWithErrorHandling(() -> compressed_fhe_uint192_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint192_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint192 decompress() {
    FheUint192 fhe = new FheUint192();
    executeWithErrorHandling(() -> compressed_fhe_uint192_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint192 clone() {
    CompressedFheUint192 cloned = new CompressedFheUint192();
    executeWithErrorHandling(() -> compressed_fhe_uint192_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
