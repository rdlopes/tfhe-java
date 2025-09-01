package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt120 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt120() {
    super(CompressedFheInt120.class, TfheWrapper::compressed_fhe_int120_destroy);
  }

  public static CompressedFheInt120 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt120 compressedFheInt120 = new CompressedFheInt120();
    executeWithErrorHandling(() -> compressed_fhe_int120_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressedFheInt120.getAddress()));
    return compressedFheInt120;
  }

  public static CompressedFheInt120 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt120 compressedFheInt120 = new CompressedFheInt120();
    executeWithErrorHandling(() -> compressed_fhe_int120_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt120.getAddress()));
    return compressedFheInt120;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int120_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt120 decompress() {
    FheInt120 fheint120 = new FheInt120();
    executeWithErrorHandling(() -> compressed_fhe_int120_decompress(getValue(), fheint120.getAddress()));
    return fheint120;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt120 clone() {
    CompressedFheInt120 compressedFheInt120 = new CompressedFheInt120();
    executeWithErrorHandling(() -> compressed_fhe_int120_clone(getValue(), compressedFheInt120.getAddress()));
    return compressedFheInt120;
  }
}