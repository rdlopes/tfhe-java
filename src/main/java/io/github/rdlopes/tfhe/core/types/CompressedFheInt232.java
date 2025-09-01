package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt232 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt232() {
    super(CompressedFheInt232.class, TfheWrapper::compressed_fhe_int232_destroy);
  }

  public static CompressedFheInt232 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt232 compressedFheInt232 = new CompressedFheInt232();
    executeWithErrorHandling(() -> compressed_fhe_int232_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt232.getAddress()));
    return compressedFheInt232;
  }

  public static CompressedFheInt232 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt232 compressedFheInt232 = new CompressedFheInt232();
    executeWithErrorHandling(() -> compressed_fhe_int232_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt232.getAddress()));
    return compressedFheInt232;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int232_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt232 decompress() {
    FheInt232 fheint232 = new FheInt232();
    executeWithErrorHandling(() -> compressed_fhe_int232_decompress(getValue(), fheint232.getAddress()));
    return fheint232;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt232 clone() {
    CompressedFheInt232 compressedFheInt232 = new CompressedFheInt232();
    executeWithErrorHandling(() -> compressed_fhe_int232_clone(getValue(), compressedFheInt232.getAddress()));
    return compressedFheInt232;
  }
}