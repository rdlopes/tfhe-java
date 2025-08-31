package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint32 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint32() {
    super(CompressedFheUint32.class, TfheWrapper::compressed_fhe_uint32_destroy);
  }

  public static CompressedFheUint32 encryptWithClientKey(int clearValue, ClientKey clientKey) {
    CompressedFheUint32 compressedFheUint32 = new CompressedFheUint32();
    executeWithErrorHandling(() -> compressed_fhe_uint32_try_encrypt_with_client_key_u32(clearValue, clientKey.getValue(), compressedFheUint32.getAddress()));
    return compressedFheUint32;
  }

  public static CompressedFheUint32 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint32 compressedFheUint32 = new CompressedFheUint32();
    executeWithErrorHandling(() -> compressed_fhe_uint32_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint32.getAddress()));
    return compressedFheUint32;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint32_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.cleanNativeResources();
    return dynamicBufferView;
  }

  public FheUint32 decompress() {
    FheUint32 fheUint32 = new FheUint32();
    executeWithErrorHandling(() -> compressed_fhe_uint32_decompress(getValue(), fheUint32.getAddress()));
    return fheUint32;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint32 clone() {
    CompressedFheUint32 compressedFheUint32 = new CompressedFheUint32();
    executeWithErrorHandling(() -> compressed_fhe_uint32_clone(getValue(), compressedFheUint32.getAddress()));
    return compressedFheUint32;
  }
}
