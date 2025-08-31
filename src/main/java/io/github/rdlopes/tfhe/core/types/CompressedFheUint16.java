package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint16 extends AddressLayoutPointer implements Cloneable {

  public CompressedFheUint16() {
    super(CompressedFheUint16.class, TfheWrapper::compressed_fhe_uint16_destroy);
  }

  public static CompressedFheUint16 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    CompressedFheUint16 compressedFheUint16 = new CompressedFheUint16();
    executeWithErrorHandling(() -> compressed_fhe_uint16_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), compressedFheUint16.getAddress()));
    return compressedFheUint16;
  }

  public static CompressedFheUint16 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint16 compressedFheUint16 = new CompressedFheUint16();
    executeWithErrorHandling(() -> compressed_fhe_uint16_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint16.getAddress()));
    return compressedFheUint16;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint16_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.cleanNativeResources();
    return dynamicBufferView;
  }

  public FheUint16 decompress() {
    FheUint16 fheUint16 = new FheUint16();
    executeWithErrorHandling(() -> compressed_fhe_uint16_decompress(getValue(), fheUint16.getAddress()));
    return fheUint16;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint16 clone() {
    CompressedFheUint16 compressedFheUint16 = new CompressedFheUint16();
    executeWithErrorHandling(() -> compressed_fhe_uint16_clone(getValue(), compressedFheUint16.getAddress()));
    return compressedFheUint16;
  }
}
