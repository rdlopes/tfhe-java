package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt2 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt2() {
    super(CompressedFheInt2.class, TfheWrapper::compressed_fhe_int2_destroy);
  }

  public static CompressedFheInt2 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    CompressedFheInt2 compressed = new CompressedFheInt2();
    executeWithErrorHandling(() -> compressed_fhe_int2_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt2 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt2 compressed = new CompressedFheInt2();
    executeWithErrorHandling(() -> compressed_fhe_int2_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int2_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt2 decompress() {
    FheInt2 fhe = new FheInt2();
    executeWithErrorHandling(() -> compressed_fhe_int2_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt2 clone() {
    CompressedFheInt2 cloned = new CompressedFheInt2();
    executeWithErrorHandling(() -> compressed_fhe_int2_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
