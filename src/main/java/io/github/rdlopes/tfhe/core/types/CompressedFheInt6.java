package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt6 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt6() {
    super(CompressedFheInt6.class, TfheWrapper::compressed_fhe_int6_destroy);
  }

  public static CompressedFheInt6 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    CompressedFheInt6 compressedFheInt6 = new CompressedFheInt6();
    executeWithErrorHandling(() -> compressed_fhe_int6_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), compressedFheInt6.getAddress()));
    return compressedFheInt6;
  }

  public static CompressedFheInt6 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt6 compressedFheInt6 = new CompressedFheInt6();
    executeWithErrorHandling(() -> compressed_fhe_int6_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt6.getAddress()));
    return compressedFheInt6;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int6_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt6 decompress() {
    FheInt6 fheint6 = new FheInt6();
    executeWithErrorHandling(() -> compressed_fhe_int6_decompress(getValue(), fheint6.getAddress()));
    return fheint6;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt6 clone() {
    CompressedFheInt6 compressedFheInt6 = new CompressedFheInt6();
    executeWithErrorHandling(() -> compressed_fhe_int6_clone(getValue(), compressedFheInt6.getAddress()));
    return compressedFheInt6;
  }
}