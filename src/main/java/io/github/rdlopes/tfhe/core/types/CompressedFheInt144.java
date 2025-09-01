package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt144 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt144() {
    super(CompressedFheInt144.class, TfheWrapper::compressed_fhe_int144_destroy);
  }

  public static CompressedFheInt144 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt144 compressedFheInt144 = new CompressedFheInt144();
    executeWithErrorHandling(() -> compressed_fhe_int144_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt144.getAddress()));
    return compressedFheInt144;
  }

  public static CompressedFheInt144 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt144 compressedFheInt144 = new CompressedFheInt144();
    executeWithErrorHandling(() -> compressed_fhe_int144_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt144.getAddress()));
    return compressedFheInt144;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int144_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt144 decompress() {
    FheInt144 fheint144 = new FheInt144();
    executeWithErrorHandling(() -> compressed_fhe_int144_decompress(getValue(), fheint144.getAddress()));
    return fheint144;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt144 clone() {
    CompressedFheInt144 compressedFheInt144 = new CompressedFheInt144();
    executeWithErrorHandling(() -> compressed_fhe_int144_clone(getValue(), compressedFheInt144.getAddress()));
    return compressedFheInt144;
  }
}