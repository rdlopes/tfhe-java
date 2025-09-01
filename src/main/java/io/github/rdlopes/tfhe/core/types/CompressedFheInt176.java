package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt176 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt176() {
    super(CompressedFheInt176.class, TfheWrapper::compressed_fhe_int176_destroy);
  }

  public static CompressedFheInt176 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt176 compressedFheInt176 = new CompressedFheInt176();
    executeWithErrorHandling(() -> compressed_fhe_int176_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt176.getAddress()));
    return compressedFheInt176;
  }

  public static CompressedFheInt176 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt176 compressedFheInt176 = new CompressedFheInt176();
    executeWithErrorHandling(() -> compressed_fhe_int176_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt176.getAddress()));
    return compressedFheInt176;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int176_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt176 decompress() {
    FheInt176 fheint176 = new FheInt176();
    executeWithErrorHandling(() -> compressed_fhe_int176_decompress(getValue(), fheint176.getAddress()));
    return fheint176;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt176 clone() {
    CompressedFheInt176 compressedFheInt176 = new CompressedFheInt176();
    executeWithErrorHandling(() -> compressed_fhe_int176_clone(getValue(), compressedFheInt176.getAddress()));
    return compressedFheInt176;
  }
}