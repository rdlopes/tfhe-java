package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt184 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt184() {
    super(CompressedFheInt184.class, TfheWrapper::compressed_fhe_int184_destroy);
  }

  public static CompressedFheInt184 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt184 compressedFheInt184 = new CompressedFheInt184();
    executeWithErrorHandling(() -> compressed_fhe_int184_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt184.getAddress()));
    return compressedFheInt184;
  }

  public static CompressedFheInt184 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt184 compressedFheInt184 = new CompressedFheInt184();
    executeWithErrorHandling(() -> compressed_fhe_int184_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt184.getAddress()));
    return compressedFheInt184;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int184_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt184 decompress() {
    FheInt184 fheint184 = new FheInt184();
    executeWithErrorHandling(() -> compressed_fhe_int184_decompress(getValue(), fheint184.getAddress()));
    return fheint184;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt184 clone() {
    CompressedFheInt184 compressedFheInt184 = new CompressedFheInt184();
    executeWithErrorHandling(() -> compressed_fhe_int184_clone(getValue(), compressedFheInt184.getAddress()));
    return compressedFheInt184;
  }
}