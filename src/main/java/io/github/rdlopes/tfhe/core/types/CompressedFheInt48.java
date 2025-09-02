package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt48 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt48() {
    super(CompressedFheInt48.class, TfheWrapper::compressed_fhe_int48_destroy);
  }

  public static CompressedFheInt48 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    CompressedFheInt48 compressed = new CompressedFheInt48();
    executeWithErrorHandling(() -> compressed_fhe_int48_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt48 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt48 compressed = new CompressedFheInt48();
    executeWithErrorHandling(() -> compressed_fhe_int48_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int48_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt48 decompress() {
    FheInt48 fhe = new FheInt48();
    executeWithErrorHandling(() -> compressed_fhe_int48_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt48 clone() {
    CompressedFheInt48 cloned = new CompressedFheInt48();
    executeWithErrorHandling(() -> compressed_fhe_int48_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
