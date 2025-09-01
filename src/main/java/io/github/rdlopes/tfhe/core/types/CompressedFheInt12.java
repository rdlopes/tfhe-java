package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt12 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt12() {
    super(CompressedFheInt12.class, TfheWrapper::compressed_fhe_int12_destroy);
  }

  public static CompressedFheInt12 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    CompressedFheInt12 compressedFheInt12 = new CompressedFheInt12();
    executeWithErrorHandling(() -> compressed_fhe_int12_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), compressedFheInt12.getAddress()));
    return compressedFheInt12;
  }

  public static CompressedFheInt12 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt12 compressedFheInt12 = new CompressedFheInt12();
    executeWithErrorHandling(() -> compressed_fhe_int12_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt12.getAddress()));
    return compressedFheInt12;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int12_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt12 decompress() {
    FheInt12 fheint12 = new FheInt12();
    executeWithErrorHandling(() -> compressed_fhe_int12_decompress(getValue(), fheint12.getAddress()));
    return fheint12;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt12 clone() {
    CompressedFheInt12 compressedFheInt12 = new CompressedFheInt12();
    executeWithErrorHandling(() -> compressed_fhe_int12_clone(getValue(), compressedFheInt12.getAddress()));
    return compressedFheInt12;
  }
}