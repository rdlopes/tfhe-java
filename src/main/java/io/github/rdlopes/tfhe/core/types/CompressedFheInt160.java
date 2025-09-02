package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt160 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt160() {
    super(CompressedFheInt160.class, TfheWrapper::compressed_fhe_int160_destroy);
  }

  public static CompressedFheInt160 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt160 compressed = new CompressedFheInt160();
    executeWithErrorHandling(() -> compressed_fhe_int160_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt160 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt160 compressed = new CompressedFheInt160();
    executeWithErrorHandling(() -> compressed_fhe_int160_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int160_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt160 decompress() {
    FheInt160 fhe = new FheInt160();
    executeWithErrorHandling(() -> compressed_fhe_int160_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt160 clone() {
    CompressedFheInt160 cloned = new CompressedFheInt160();
    executeWithErrorHandling(() -> compressed_fhe_int160_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
