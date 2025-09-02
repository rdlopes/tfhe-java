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
    CompressedFheInt8 compressed = new CompressedFheInt8();
    executeWithErrorHandling(() -> compressed_fhe_int8_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt8 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt8 compressed = new CompressedFheInt8();
    executeWithErrorHandling(() -> compressed_fhe_int8_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int8_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt8 decompress() {
    FheInt8 fhe = new FheInt8();
    executeWithErrorHandling(() -> compressed_fhe_int8_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt8 clone() {
    CompressedFheInt8 cloned = new CompressedFheInt8();
    executeWithErrorHandling(() -> compressed_fhe_int8_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
