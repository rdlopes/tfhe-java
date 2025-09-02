package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt152 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt152() {
    super(CompressedFheInt152.class, TfheWrapper::compressed_fhe_int152_destroy);
  }

  public static CompressedFheInt152 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt152 compressed = new CompressedFheInt152();
    executeWithErrorHandling(() -> compressed_fhe_int152_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt152 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt152 compressed = new CompressedFheInt152();
    executeWithErrorHandling(() -> compressed_fhe_int152_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int152_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt152 decompress() {
    FheInt152 fhe = new FheInt152();
    executeWithErrorHandling(() -> compressed_fhe_int152_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt152 clone() {
    CompressedFheInt152 cloned = new CompressedFheInt152();
    executeWithErrorHandling(() -> compressed_fhe_int152_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
