package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint8 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint8() {
    super(CompressedFheUint8.class, TfheWrapper::compressed_fhe_uint8_destroy);
  }

  public static CompressedFheUint8 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    CompressedFheUint8 compressedFheUint8 = new CompressedFheUint8();
    executeWithErrorHandling(() -> compressed_fhe_uint8_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), compressedFheUint8.getAddress()));
    return compressedFheUint8;
  }

  public static CompressedFheUint8 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint8 compressedFheUint8 = new CompressedFheUint8();
    executeWithErrorHandling(() -> compressed_fhe_uint8_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint8.getAddress()));
    return compressedFheUint8;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint8_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint8 decompress() {
    FheUint8 fheUint8 = new FheUint8();
    executeWithErrorHandling(() -> compressed_fhe_uint8_decompress(getValue(), fheUint8.getAddress()));
    return fheUint8;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint8 clone() {
    CompressedFheUint8 compressedFheUint8 = new CompressedFheUint8();
    executeWithErrorHandling(() -> compressed_fhe_uint8_clone(getValue(), compressedFheUint8.getAddress()));
    return compressedFheUint8;
  }
}
