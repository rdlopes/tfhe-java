package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint48 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint48() {
    super(CompressedFheUint48.class, TfheWrapper::compressed_fhe_uint48_destroy);
  }

  public static CompressedFheUint48 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    CompressedFheUint48 compressed = new CompressedFheUint48();
    executeWithErrorHandling(() -> compressed_fhe_uint48_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint48 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint48 compressed = new CompressedFheUint48();
    executeWithErrorHandling(() -> compressed_fhe_uint48_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint48_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint48 decompress() {
    FheUint48 fhe = new FheUint48();
    executeWithErrorHandling(() -> compressed_fhe_uint48_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint48 clone() {
    CompressedFheUint48 cloned = new CompressedFheUint48();
    executeWithErrorHandling(() -> compressed_fhe_uint48_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
