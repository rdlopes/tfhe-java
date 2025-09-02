package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt4 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt4() {
    super(CompressedFheInt4.class, TfheWrapper::compressed_fhe_int4_destroy);
  }

  public static CompressedFheInt4 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    CompressedFheInt4 compressed = new CompressedFheInt4();
    executeWithErrorHandling(() -> compressed_fhe_int4_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt4 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt4 compressed = new CompressedFheInt4();
    executeWithErrorHandling(() -> compressed_fhe_int4_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int4_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt4 decompress() {
    FheInt4 fhe = new FheInt4();
    executeWithErrorHandling(() -> compressed_fhe_int4_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt4 clone() {
    CompressedFheInt4 cloned = new CompressedFheInt4();
    executeWithErrorHandling(() -> compressed_fhe_int4_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
