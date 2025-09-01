package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt14 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt14() {
    super(CompressedFheInt14.class, TfheWrapper::compressed_fhe_int14_destroy);
  }

  public static CompressedFheInt14 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    CompressedFheInt14 compressedFheInt14 = new CompressedFheInt14();
    executeWithErrorHandling(() -> compressed_fhe_int14_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), compressedFheInt14.getAddress()));
    return compressedFheInt14;
  }

  public static CompressedFheInt14 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt14 compressedFheInt14 = new CompressedFheInt14();
    executeWithErrorHandling(() -> compressed_fhe_int14_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt14.getAddress()));
    return compressedFheInt14;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int14_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt14 decompress() {
    FheInt14 fheint14 = new FheInt14();
    executeWithErrorHandling(() -> compressed_fhe_int14_decompress(getValue(), fheint14.getAddress()));
    return fheint14;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt14 clone() {
    CompressedFheInt14 compressedFheInt14 = new CompressedFheInt14();
    executeWithErrorHandling(() -> compressed_fhe_int14_clone(getValue(), compressedFheInt14.getAddress()));
    return compressedFheInt14;
  }
}