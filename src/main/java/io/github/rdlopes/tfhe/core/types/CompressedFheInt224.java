package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt224 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt224() {
    super(CompressedFheInt224.class, TfheWrapper::compressed_fhe_int224_destroy);
  }

  public static CompressedFheInt224 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt224 compressed = new CompressedFheInt224();
    executeWithErrorHandling(() -> compressed_fhe_int224_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt224 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt224 compressed = new CompressedFheInt224();
    executeWithErrorHandling(() -> compressed_fhe_int224_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int224_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt224 decompress() {
    FheInt224 fhe = new FheInt224();
    executeWithErrorHandling(() -> compressed_fhe_int224_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt224 clone() {
    CompressedFheInt224 cloned = new CompressedFheInt224();
    executeWithErrorHandling(() -> compressed_fhe_int224_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
