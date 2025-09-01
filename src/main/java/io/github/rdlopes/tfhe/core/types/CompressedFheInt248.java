package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt248 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt248() {
    super(CompressedFheInt248.class, TfheWrapper::compressed_fhe_int248_destroy);
  }

  public static CompressedFheInt248 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt248 compressedFheInt248 = new CompressedFheInt248();
    executeWithErrorHandling(() -> compressed_fhe_int248_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt248.getAddress()));
    return compressedFheInt248;
  }

  public static CompressedFheInt248 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt248 compressedFheInt248 = new CompressedFheInt248();
    executeWithErrorHandling(() -> compressed_fhe_int248_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt248.getAddress()));
    return compressedFheInt248;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int248_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt248 decompress() {
    FheInt248 fheint248 = new FheInt248();
    executeWithErrorHandling(() -> compressed_fhe_int248_decompress(getValue(), fheint248.getAddress()));
    return fheint248;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt248 clone() {
    CompressedFheInt248 compressedFheInt248 = new CompressedFheInt248();
    executeWithErrorHandling(() -> compressed_fhe_int248_clone(getValue(), compressedFheInt248.getAddress()));
    return compressedFheInt248;
  }
}