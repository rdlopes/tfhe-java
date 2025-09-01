package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt512 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt512() {
    super(CompressedFheInt512.class, TfheWrapper::compressed_fhe_int512_destroy);
  }

  public static CompressedFheInt512 encryptWithClientKey(I512 clearValue, ClientKey clientKey) {
    CompressedFheInt512 compressedFheInt512 = new CompressedFheInt512();
    executeWithErrorHandling(() -> compressed_fhe_int512_try_encrypt_with_client_key_i512(clearValue.getAddress(), clientKey.getValue(), compressedFheInt512.getAddress()));
    return compressedFheInt512;
  }

  public static CompressedFheInt512 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt512 compressedFheInt512 = new CompressedFheInt512();
    executeWithErrorHandling(() -> compressed_fhe_int512_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt512.getAddress()));
    return compressedFheInt512;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int512_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt512 decompress() {
    FheInt512 fheint512 = new FheInt512();
    executeWithErrorHandling(() -> compressed_fhe_int512_decompress(getValue(), fheint512.getAddress()));
    return fheint512;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt512 clone() {
    CompressedFheInt512 compressedFheInt512 = new CompressedFheInt512();
    executeWithErrorHandling(() -> compressed_fhe_int512_clone(getValue(), compressedFheInt512.getAddress()));
    return compressedFheInt512;
  }
}