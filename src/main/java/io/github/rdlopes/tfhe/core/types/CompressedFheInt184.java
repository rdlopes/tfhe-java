package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt184 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt184() {
    super(CompressedFheInt184.class, TfheWrapper::compressed_fhe_int184_destroy);
  }

  public static CompressedFheInt184 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt184 compressed = new CompressedFheInt184();
    executeWithErrorHandling(() -> compressed_fhe_int184_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt184 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt184 compressed = new CompressedFheInt184();
    executeWithErrorHandling(() -> compressed_fhe_int184_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int184_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt184 decompress() {
    FheInt184 fhe = new FheInt184();
    executeWithErrorHandling(() -> compressed_fhe_int184_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt184 clone() {
    CompressedFheInt184 cloned = new CompressedFheInt184();
    executeWithErrorHandling(() -> compressed_fhe_int184_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
