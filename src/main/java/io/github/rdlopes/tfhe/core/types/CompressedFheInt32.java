package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt32 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt32() {
    super(CompressedFheInt32.class, TfheWrapper::compressed_fhe_int32_destroy);
  }

  public static CompressedFheInt32 encryptWithClientKey(int clearValue, ClientKey clientKey) {
    CompressedFheInt32 compressed = new CompressedFheInt32();
    executeWithErrorHandling(() -> compressed_fhe_int32_try_encrypt_with_client_key_i32(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt32 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt32 compressed = new CompressedFheInt32();
    executeWithErrorHandling(() -> compressed_fhe_int32_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int32_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt32 decompress() {
    FheInt32 fhe = new FheInt32();
    executeWithErrorHandling(() -> compressed_fhe_int32_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt32 clone() {
    CompressedFheInt32 cloned = new CompressedFheInt32();
    executeWithErrorHandling(() -> compressed_fhe_int32_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
