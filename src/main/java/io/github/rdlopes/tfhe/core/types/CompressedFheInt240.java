package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt240 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt240() {
    super(CompressedFheInt240.class, TfheWrapper::compressed_fhe_int240_destroy);
  }

  public static CompressedFheInt240 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt240 compressed = new CompressedFheInt240();
    executeWithErrorHandling(() -> compressed_fhe_int240_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt240 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt240 compressed = new CompressedFheInt240();
    executeWithErrorHandling(() -> compressed_fhe_int240_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int240_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt240 decompress() {
    FheInt240 fhe = new FheInt240();
    executeWithErrorHandling(() -> compressed_fhe_int240_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt240 clone() {
    CompressedFheInt240 cloned = new CompressedFheInt240();
    executeWithErrorHandling(() -> compressed_fhe_int240_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
