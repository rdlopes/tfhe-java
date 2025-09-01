package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt40 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt40() {
    super(CompressedFheInt40.class, TfheWrapper::compressed_fhe_int40_destroy);
  }

  public static CompressedFheInt40 encryptWithClientKey(long clearValue, ClientKey clientKey) {
    CompressedFheInt40 compressedFheInt40 = new CompressedFheInt40();
    executeWithErrorHandling(() -> compressed_fhe_int40_try_encrypt_with_client_key_i64(clearValue, clientKey.getValue(), compressedFheInt40.getAddress()));
    return compressedFheInt40;
  }

  public static CompressedFheInt40 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt40 compressedFheInt40 = new CompressedFheInt40();
    executeWithErrorHandling(() -> compressed_fhe_int40_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt40.getAddress()));
    return compressedFheInt40;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int40_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt40 decompress() {
    FheInt40 fheint40 = new FheInt40();
    executeWithErrorHandling(() -> compressed_fhe_int40_decompress(getValue(), fheint40.getAddress()));
    return fheint40;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt40 clone() {
    CompressedFheInt40 compressedFheInt40 = new CompressedFheInt40();
    executeWithErrorHandling(() -> compressed_fhe_int40_clone(getValue(), compressedFheInt40.getAddress()));
    return compressedFheInt40;
  }
}