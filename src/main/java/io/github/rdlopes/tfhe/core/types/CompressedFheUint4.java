package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint4 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint4() {
    super(CompressedFheUint4.class, TfheWrapper::compressed_fhe_uint4_destroy);
  }

  public static CompressedFheUint4 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    CompressedFheUint4 compressedFheUint4 = new CompressedFheUint4();
    executeWithErrorHandling(() -> compressed_fhe_uint4_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), compressedFheUint4.getAddress()));
    return compressedFheUint4;
  }

  public static CompressedFheUint4 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint4 compressedFheUint4 = new CompressedFheUint4();
    executeWithErrorHandling(() -> compressed_fhe_uint4_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint4.getAddress()));
    return compressedFheUint4;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint4_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.cleanNativeResources();
    return dynamicBufferView;
  }

  public FheUint4 decompress() {
    FheUint4 fheUint4 = new FheUint4();
    executeWithErrorHandling(() -> compressed_fhe_uint4_decompress(getValue(), fheUint4.getAddress()));
    return fheUint4;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint4 clone() {
    CompressedFheUint4 compressedFheUint4 = new CompressedFheUint4();
    executeWithErrorHandling(() -> compressed_fhe_uint4_clone(getValue(), compressedFheUint4.getAddress()));
    return compressedFheUint4;
  }
}
