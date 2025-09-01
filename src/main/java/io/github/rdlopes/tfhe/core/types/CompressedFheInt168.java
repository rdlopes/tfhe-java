package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt168 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt168() {
    super(CompressedFheInt168.class, TfheWrapper::compressed_fhe_int168_destroy);
  }

  public static CompressedFheInt168 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt168 compressedFheInt168 = new CompressedFheInt168();
    executeWithErrorHandling(() -> compressed_fhe_int168_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt168.getAddress()));
    return compressedFheInt168;
  }

  public static CompressedFheInt168 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt168 compressedFheInt168 = new CompressedFheInt168();
    executeWithErrorHandling(() -> compressed_fhe_int168_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt168.getAddress()));
    return compressedFheInt168;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int168_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt168 decompress() {
    FheInt168 fheint168 = new FheInt168();
    executeWithErrorHandling(() -> compressed_fhe_int168_decompress(getValue(), fheint168.getAddress()));
    return fheint168;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt168 clone() {
    CompressedFheInt168 compressedFheInt168 = new CompressedFheInt168();
    executeWithErrorHandling(() -> compressed_fhe_int168_clone(getValue(), compressedFheInt168.getAddress()));
    return compressedFheInt168;
  }
}