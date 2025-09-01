package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt216 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt216() {
    super(CompressedFheInt216.class, TfheWrapper::compressed_fhe_int216_destroy);
  }

  public static CompressedFheInt216 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt216 compressedFheInt216 = new CompressedFheInt216();
    executeWithErrorHandling(() -> compressed_fhe_int216_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt216.getAddress()));
    return compressedFheInt216;
  }

  public static CompressedFheInt216 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt216 compressedFheInt216 = new CompressedFheInt216();
    executeWithErrorHandling(() -> compressed_fhe_int216_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt216.getAddress()));
    return compressedFheInt216;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int216_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt216 decompress() {
    FheInt216 fheint216 = new FheInt216();
    executeWithErrorHandling(() -> compressed_fhe_int216_decompress(getValue(), fheint216.getAddress()));
    return fheint216;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt216 clone() {
    CompressedFheInt216 compressedFheInt216 = new CompressedFheInt216();
    executeWithErrorHandling(() -> compressed_fhe_int216_clone(getValue(), compressedFheInt216.getAddress()));
    return compressedFheInt216;
  }
}