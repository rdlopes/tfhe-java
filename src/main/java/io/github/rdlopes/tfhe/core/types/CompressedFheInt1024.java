package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt1024 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt1024() {
    super(CompressedFheInt1024.class, TfheWrapper::compressed_fhe_int1024_destroy);
  }

  public static CompressedFheInt1024 encryptWithClientKey(I1024 clearValue, ClientKey clientKey) {
    CompressedFheInt1024 compressedFheInt1024 = new CompressedFheInt1024();
    executeWithErrorHandling(() -> compressed_fhe_int1024_try_encrypt_with_client_key_i1024(clearValue.getAddress(), clientKey.getValue(), compressedFheInt1024.getAddress()));
    return compressedFheInt1024;
  }

  public static CompressedFheInt1024 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt1024 compressedFheInt1024 = new CompressedFheInt1024();
    executeWithErrorHandling(() -> compressed_fhe_int1024_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt1024.getAddress()));
    return compressedFheInt1024;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int1024_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt1024 decompress() {
    FheInt1024 fheint1024 = new FheInt1024();
    executeWithErrorHandling(() -> compressed_fhe_int1024_decompress(getValue(), fheint1024.getAddress()));
    return fheint1024;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt1024 clone() {
    CompressedFheInt1024 compressedFheInt1024 = new CompressedFheInt1024();
    executeWithErrorHandling(() -> compressed_fhe_int1024_clone(getValue(), compressedFheInt1024.getAddress()));
    return compressedFheInt1024;
  }
}