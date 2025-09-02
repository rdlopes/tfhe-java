package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt1024 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt1024() {
    super(CompressedFheInt1024.class, TfheWrapper::compressed_fhe_int1024_destroy);
  }

  public static CompressedFheInt1024 encryptWithClientKey(I1024 clearValue, ClientKey clientKey) {
    CompressedFheInt1024 compressed = new CompressedFheInt1024();
    executeWithErrorHandling(() -> compressed_fhe_int1024_try_encrypt_with_client_key_i1024(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt1024 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt1024 compressed = new CompressedFheInt1024();
    executeWithErrorHandling(() -> compressed_fhe_int1024_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int1024_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt1024 decompress() {
    FheInt1024 fhe = new FheInt1024();
    executeWithErrorHandling(() -> compressed_fhe_int1024_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt1024 clone() {
    CompressedFheInt1024 cloned = new CompressedFheInt1024();
    executeWithErrorHandling(() -> compressed_fhe_int1024_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
