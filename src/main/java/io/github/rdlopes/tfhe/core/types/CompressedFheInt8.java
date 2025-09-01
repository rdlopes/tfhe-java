package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt8 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt8() {
    super(CompressedFheInt8.class, TfheWrapper::compressed_fhe_int8_destroy);
  }

  public static CompressedFheInt8 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    CompressedFheInt8 compressedFheInt8 = new CompressedFheInt8();
    executeWithErrorHandling(() -> compressed_fhe_int8_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), compressedFheInt8.getAddress()));
    return compressedFheInt8;
  }

  public static CompressedFheInt8 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt8 compressedFheInt8 = new CompressedFheInt8();
    executeWithErrorHandling(() -> compressed_fhe_int8_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt8.getAddress()));
    return compressedFheInt8;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int8_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt8 decompress() {
    FheInt8 fheint8 = new FheInt8();
    executeWithErrorHandling(() -> compressed_fhe_int8_decompress(getValue(), fheint8.getAddress()));
    return fheint8;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt8 clone() {
    CompressedFheInt8 compressedFheInt8 = new CompressedFheInt8();
    executeWithErrorHandling(() -> compressed_fhe_int8_clone(getValue(), compressedFheInt8.getAddress()));
    return compressedFheInt8;
  }
}