package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint224 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint224() {
    super(CompressedFheUint224.class, TfheWrapper::compressed_fhe_uint224_destroy);
  }

  public static CompressedFheUint224 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint224 compressed = new CompressedFheUint224();
    executeWithErrorHandling(() -> compressed_fhe_uint224_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint224 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint224 compressed = new CompressedFheUint224();
    executeWithErrorHandling(() -> compressed_fhe_uint224_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint224_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint224 decompress() {
    FheUint224 fhe = new FheUint224();
    executeWithErrorHandling(() -> compressed_fhe_uint224_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint224 clone() {
    CompressedFheUint224 cloned = new CompressedFheUint224();
    executeWithErrorHandling(() -> compressed_fhe_uint224_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
