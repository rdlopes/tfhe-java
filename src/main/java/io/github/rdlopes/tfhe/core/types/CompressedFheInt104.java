package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt104 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt104() {
    super(CompressedFheInt104.class, TfheWrapper::compressed_fhe_int104_destroy);
  }

  public static CompressedFheInt104 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt104 compressedFheInt104 = new CompressedFheInt104();
    executeWithErrorHandling(() -> compressed_fhe_int104_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressedFheInt104.getAddress()));
    return compressedFheInt104;
  }

  public static CompressedFheInt104 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt104 compressedFheInt104 = new CompressedFheInt104();
    executeWithErrorHandling(() -> compressed_fhe_int104_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt104.getAddress()));
    return compressedFheInt104;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int104_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt104 decompress() {
    FheInt104 fheint104 = new FheInt104();
    executeWithErrorHandling(() -> compressed_fhe_int104_decompress(getValue(), fheint104.getAddress()));
    return fheint104;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt104 clone() {
    CompressedFheInt104 compressedFheInt104 = new CompressedFheInt104();
    executeWithErrorHandling(() -> compressed_fhe_int104_clone(getValue(), compressedFheInt104.getAddress()));
    return compressedFheInt104;
  }
}