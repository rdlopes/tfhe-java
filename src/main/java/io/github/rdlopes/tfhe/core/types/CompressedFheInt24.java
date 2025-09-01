package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt24 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt24() {
    super(CompressedFheInt24.class, TfheWrapper::compressed_fhe_int24_destroy);
  }

  public static CompressedFheInt24 encryptWithClientKey(int clearValue, ClientKey clientKey) {
    CompressedFheInt24 compressedFheInt24 = new CompressedFheInt24();
    executeWithErrorHandling(() -> compressed_fhe_int24_try_encrypt_with_client_key_i32(clearValue, clientKey.getValue(), compressedFheInt24.getAddress()));
    return compressedFheInt24;
  }

  public static CompressedFheInt24 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt24 compressedFheInt24 = new CompressedFheInt24();
    executeWithErrorHandling(() -> compressed_fhe_int24_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt24.getAddress()));
    return compressedFheInt24;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int24_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt24 decompress() {
    FheInt24 fheint24 = new FheInt24();
    executeWithErrorHandling(() -> compressed_fhe_int24_decompress(getValue(), fheint24.getAddress()));
    return fheint24;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt24 clone() {
    CompressedFheInt24 compressedFheInt24 = new CompressedFheInt24();
    executeWithErrorHandling(() -> compressed_fhe_int24_clone(getValue(), compressedFheInt24.getAddress()));
    return compressedFheInt24;
  }
}