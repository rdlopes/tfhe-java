package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt192 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt192() {
    super(CompressedFheInt192.class, TfheWrapper::compressed_fhe_int192_destroy);
  }

  public static CompressedFheInt192 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt192 compressedFheInt192 = new CompressedFheInt192();
    executeWithErrorHandling(() -> compressed_fhe_int192_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt192.getAddress()));
    return compressedFheInt192;
  }

  public static CompressedFheInt192 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt192 compressedFheInt192 = new CompressedFheInt192();
    executeWithErrorHandling(() -> compressed_fhe_int192_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt192.getAddress()));
    return compressedFheInt192;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int192_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt192 decompress() {
    FheInt192 fheint192 = new FheInt192();
    executeWithErrorHandling(() -> compressed_fhe_int192_decompress(getValue(), fheint192.getAddress()));
    return fheint192;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt192 clone() {
    CompressedFheInt192 compressedFheInt192 = new CompressedFheInt192();
    executeWithErrorHandling(() -> compressed_fhe_int192_clone(getValue(), compressedFheInt192.getAddress()));
    return compressedFheInt192;
  }
}