package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt168 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt168() {
    super(CompressedFheInt168.class, TfheWrapper::compressed_fhe_int168_destroy);
  }

  public static CompressedFheInt168 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt168 compressed = new CompressedFheInt168();
    executeWithErrorHandling(() -> compressed_fhe_int168_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt168 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt168 compressed = new CompressedFheInt168();
    executeWithErrorHandling(() -> compressed_fhe_int168_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int168_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt168 decompress() {
    FheInt168 fhe = new FheInt168();
    executeWithErrorHandling(() -> compressed_fhe_int168_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt168 clone() {
    CompressedFheInt168 cloned = new CompressedFheInt168();
    executeWithErrorHandling(() -> compressed_fhe_int168_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
