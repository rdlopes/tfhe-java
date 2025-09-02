package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint14 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint14() {
    super(CompressedFheUint14.class, TfheWrapper::compressed_fhe_uint14_destroy);
  }

  public static CompressedFheUint14 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    CompressedFheUint14 compressed = new CompressedFheUint14();
    executeWithErrorHandling(() -> compressed_fhe_uint14_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint14 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint14 compressed = new CompressedFheUint14();
    executeWithErrorHandling(() -> compressed_fhe_uint14_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint14_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint14 decompress() {
    FheUint14 fhe = new FheUint14();
    executeWithErrorHandling(() -> compressed_fhe_uint14_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint14 clone() {
    CompressedFheUint14 cloned = new CompressedFheUint14();
    executeWithErrorHandling(() -> compressed_fhe_uint14_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
