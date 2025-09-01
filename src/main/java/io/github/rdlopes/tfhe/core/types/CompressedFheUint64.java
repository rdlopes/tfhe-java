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
    CompressedFheUint64 compressedFheUint64 = new CompressedFheUint64();
    executeWithErrorHandling(() -> compressed_fhe_uint64_try_encrypt_with_client_key_u64(clearValue, clientKey.getValue(), compressedFheUint64.getAddress()));
    return compressedFheUint64;
  }

  public static CompressedFheUint64 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint64 compressedFheUint64 = new CompressedFheUint64();
    executeWithErrorHandling(() -> compressed_fhe_uint64_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint64.getAddress()));
    return compressedFheUint64;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint64_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint64 decompress() {
    FheUint64 fheUint64 = new FheUint64();
    executeWithErrorHandling(() -> compressed_fhe_uint64_decompress(getValue(), fheUint64.getAddress()));
    return fheUint64;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint64 clone() {
    CompressedFheUint64 compressedFheUint64 = new CompressedFheUint64();
    executeWithErrorHandling(() -> compressed_fhe_uint64_clone(getValue(), compressedFheUint64.getAddress()));
    return compressedFheUint64;
  }
}
