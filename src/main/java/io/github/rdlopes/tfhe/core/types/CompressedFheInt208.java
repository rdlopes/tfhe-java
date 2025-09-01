package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt208 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt208() {
    super(CompressedFheInt208.class, TfheWrapper::compressed_fhe_int208_destroy);
  }

  public static CompressedFheInt208 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt208 compressedFheInt208 = new CompressedFheInt208();
    executeWithErrorHandling(() -> compressed_fhe_int208_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt208.getAddress()));
    return compressedFheInt208;
  }

  public static CompressedFheInt208 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt208 compressedFheInt208 = new CompressedFheInt208();
    executeWithErrorHandling(() -> compressed_fhe_int208_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt208.getAddress()));
    return compressedFheInt208;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int208_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt208 decompress() {
    FheInt208 fheint208 = new FheInt208();
    executeWithErrorHandling(() -> compressed_fhe_int208_decompress(getValue(), fheint208.getAddress()));
    return fheint208;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt208 clone() {
    CompressedFheInt208 compressedFheInt208 = new CompressedFheInt208();
    executeWithErrorHandling(() -> compressed_fhe_int208_clone(getValue(), compressedFheInt208.getAddress()));
    return compressedFheInt208;
  }
}