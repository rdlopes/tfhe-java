package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt16 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt16() {
    super(CompressedFheInt16.class, TfheWrapper::compressed_fhe_int16_destroy);
  }

  public static CompressedFheInt16 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    CompressedFheInt16 compressed = new CompressedFheInt16();
    executeWithErrorHandling(() -> compressed_fhe_int16_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt16 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt16 compressed = new CompressedFheInt16();
    executeWithErrorHandling(() -> compressed_fhe_int16_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int16_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt16 decompress() {
    FheInt16 fhe = new FheInt16();
    executeWithErrorHandling(() -> compressed_fhe_int16_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt16 clone() {
    CompressedFheInt16 cloned = new CompressedFheInt16();
    executeWithErrorHandling(() -> compressed_fhe_int16_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
