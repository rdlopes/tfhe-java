package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint64 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint64() {
    super(CompressedFheUint64.class, TfheWrapper::compressed_fhe_uint64_destroy);
  }

  public static CompressedFheUint64 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    CompressedFheUint64 compressed = new CompressedFheUint64();
    executeWithErrorHandling(() -> compressed_fhe_uint64_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint64 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint64 compressed = new CompressedFheUint64();
    executeWithErrorHandling(() -> compressed_fhe_uint64_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint64_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint64 decompress() {
    FheUint64 fhe = new FheUint64();
    executeWithErrorHandling(() -> compressed_fhe_uint64_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint64 clone() {
    CompressedFheUint64 cloned = new CompressedFheUint64();
    executeWithErrorHandling(() -> compressed_fhe_uint64_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
