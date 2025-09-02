package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint40 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint40() {
    super(CompressedFheUint40.class, TfheWrapper::compressed_fhe_uint40_destroy);
  }

  public static CompressedFheUint40 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    CompressedFheUint40 compressed = new CompressedFheUint40();
    executeWithErrorHandling(() -> compressed_fhe_uint40_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint40 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint40 compressed = new CompressedFheUint40();
    executeWithErrorHandling(() -> compressed_fhe_uint40_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint40_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint40 decompress() {
    FheUint40 fhe = new FheUint40();
    executeWithErrorHandling(() -> compressed_fhe_uint40_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint40 clone() {
    CompressedFheUint40 cloned = new CompressedFheUint40();
    executeWithErrorHandling(() -> compressed_fhe_uint40_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
