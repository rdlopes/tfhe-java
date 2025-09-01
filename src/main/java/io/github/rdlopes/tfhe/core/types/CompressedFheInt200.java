package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt200 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt200() {
    super(CompressedFheInt200.class, TfheWrapper::compressed_fhe_int200_destroy);
  }

  public static CompressedFheInt200 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt200 compressedFheInt200 = new CompressedFheInt200();
    executeWithErrorHandling(() -> compressed_fhe_int200_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt200.getAddress()));
    return compressedFheInt200;
  }

  public static CompressedFheInt200 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt200 compressedFheInt200 = new CompressedFheInt200();
    executeWithErrorHandling(() -> compressed_fhe_int200_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt200.getAddress()));
    return compressedFheInt200;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int200_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt200 decompress() {
    FheInt200 fheint200 = new FheInt200();
    executeWithErrorHandling(() -> compressed_fhe_int200_decompress(getValue(), fheint200.getAddress()));
    return fheint200;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt200 clone() {
    CompressedFheInt200 compressedFheInt200 = new CompressedFheInt200();
    executeWithErrorHandling(() -> compressed_fhe_int200_clone(getValue(), compressedFheInt200.getAddress()));
    return compressedFheInt200;
  }
}