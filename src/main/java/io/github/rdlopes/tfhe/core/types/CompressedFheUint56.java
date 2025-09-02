package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint56 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint56() {
    super(CompressedFheUint56.class, TfheWrapper::compressed_fhe_uint56_destroy);
  }

  public static CompressedFheUint56 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    CompressedFheUint56 compressed = new CompressedFheUint56();
    executeWithErrorHandling(() -> compressed_fhe_uint56_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint56 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint56 compressed = new CompressedFheUint56();
    executeWithErrorHandling(() -> compressed_fhe_uint56_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint56_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint56 decompress() {
    FheUint56 fhe = new FheUint56();
    executeWithErrorHandling(() -> compressed_fhe_uint56_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint56 clone() {
    CompressedFheUint56 cloned = new CompressedFheUint56();
    executeWithErrorHandling(() -> compressed_fhe_uint56_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
