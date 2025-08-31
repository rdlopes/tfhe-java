package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt64 extends AddressLayoutPointer implements Cloneable {

  public CompressedFheInt64() {
    super(CompressedFheInt64.class, TfheWrapper::compressed_fhe_int64_destroy);
  }

  public static CompressedFheInt64 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    CompressedFheInt64 compressedFheInt64 = new CompressedFheInt64();
    executeWithErrorHandling(() -> compressed_fhe_int64_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), compressedFheInt64.getAddress()));
    return compressedFheInt64;
  }

  public static CompressedFheInt64 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt64 compressedFheInt64 = new CompressedFheInt64();
    executeWithErrorHandling(() -> compressed_fhe_int64_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt64.getAddress()));
    return compressedFheInt64;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int64_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    return dynamicBuffer.view();
  }

  public FheInt64 decompress() {
    FheInt64 fheInt64 = new FheInt64();
    executeWithErrorHandling(() -> compressed_fhe_int64_decompress(getValue(), fheInt64.getAddress()));
    return fheInt64;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt64 clone() {
    CompressedFheInt64 compressedFheInt64 = new CompressedFheInt64();
    executeWithErrorHandling(() -> compressed_fhe_int64_clone(getValue(), compressedFheInt64.getAddress()));
    return compressedFheInt64;
  }
}
