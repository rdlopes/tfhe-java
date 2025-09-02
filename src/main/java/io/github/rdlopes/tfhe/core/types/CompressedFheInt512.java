package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt512 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt512() {
    super(CompressedFheInt512.class, TfheWrapper::compressed_fhe_int512_destroy);
  }

  public static CompressedFheInt512 encryptWithClientKey(I1024 clearValue, ClientKey clientKey) {
    CompressedFheInt512 compressed = new CompressedFheInt512();
    executeWithErrorHandling(() -> compressed_fhe_int512_try_encrypt_with_client_key_i512(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt512 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt512 compressed = new CompressedFheInt512();
    executeWithErrorHandling(() -> compressed_fhe_int512_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int512_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt512 decompress() {
    FheInt512 fhe = new FheInt512();
    executeWithErrorHandling(() -> compressed_fhe_int512_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt512 clone() {
    CompressedFheInt512 cloned = new CompressedFheInt512();
    executeWithErrorHandling(() -> compressed_fhe_int512_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
