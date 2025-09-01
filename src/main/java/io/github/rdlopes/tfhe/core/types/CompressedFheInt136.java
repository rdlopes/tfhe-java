package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt136 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt136() {
    super(CompressedFheInt136.class, TfheWrapper::compressed_fhe_int136_destroy);
  }

  public static CompressedFheInt136 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt136 compressedFheInt136 = new CompressedFheInt136();
    executeWithErrorHandling(() -> compressed_fhe_int136_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt136.getAddress()));
    return compressedFheInt136;
  }

  public static CompressedFheInt136 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt136 compressedFheInt136 = new CompressedFheInt136();
    executeWithErrorHandling(() -> compressed_fhe_int136_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt136.getAddress()));
    return compressedFheInt136;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int136_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt136 decompress() {
    FheInt136 fheint136 = new FheInt136();
    executeWithErrorHandling(() -> compressed_fhe_int136_decompress(getValue(), fheint136.getAddress()));
    return fheint136;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt136 clone() {
    CompressedFheInt136 compressedFheInt136 = new CompressedFheInt136();
    executeWithErrorHandling(() -> compressed_fhe_int136_clone(getValue(), compressedFheInt136.getAddress()));
    return compressedFheInt136;
  }
}