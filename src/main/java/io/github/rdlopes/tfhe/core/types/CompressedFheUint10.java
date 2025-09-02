package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint10 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint10() {
    super(CompressedFheUint10.class, TfheWrapper::compressed_fhe_uint10_destroy);
  }

  public static CompressedFheUint10 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    CompressedFheUint10 compressed = new CompressedFheUint10();
    executeWithErrorHandling(() -> compressed_fhe_uint10_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint10 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint10 compressed = new CompressedFheUint10();
    executeWithErrorHandling(() -> compressed_fhe_uint10_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint10_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint10 decompress() {
    FheUint10 fhe = new FheUint10();
    executeWithErrorHandling(() -> compressed_fhe_uint10_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint10 clone() {
    CompressedFheUint10 cloned = new CompressedFheUint10();
    executeWithErrorHandling(() -> compressed_fhe_uint10_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
