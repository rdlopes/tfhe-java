package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt216 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt216() {
    super(CompressedFheInt216.class, TfheWrapper::compressed_fhe_int216_destroy);
  }

  public static CompressedFheInt216 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt216 compressed = new CompressedFheInt216();
    executeWithErrorHandling(() -> compressed_fhe_int216_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt216 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt216 compressed = new CompressedFheInt216();
    executeWithErrorHandling(() -> compressed_fhe_int216_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int216_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt216 decompress() {
    FheInt216 fhe = new FheInt216();
    executeWithErrorHandling(() -> compressed_fhe_int216_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt216 clone() {
    CompressedFheInt216 cloned = new CompressedFheInt216();
    executeWithErrorHandling(() -> compressed_fhe_int216_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
