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
    CompressedFheUint152 compressed = new CompressedFheUint152();
    executeWithErrorHandling(() -> compressed_fhe_uint152_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint152 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint152 compressed = new CompressedFheUint152();
    executeWithErrorHandling(() -> compressed_fhe_uint152_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint152_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint152 decompress() {
    FheUint152 fhe = new FheUint152();
    executeWithErrorHandling(() -> compressed_fhe_uint152_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint152 clone() {
    CompressedFheUint152 cloned = new CompressedFheUint152();
    executeWithErrorHandling(() -> compressed_fhe_uint152_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
