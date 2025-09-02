package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt208 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt208() {
    super(CompressedFheInt208.class, TfheWrapper::compressed_fhe_int208_destroy);
  }

  public static CompressedFheInt208 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt208 compressed = new CompressedFheInt208();
    executeWithErrorHandling(() -> compressed_fhe_int208_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt208 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt208 compressed = new CompressedFheInt208();
    executeWithErrorHandling(() -> compressed_fhe_int208_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int208_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt208 decompress() {
    FheInt208 fhe = new FheInt208();
    executeWithErrorHandling(() -> compressed_fhe_int208_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt208 clone() {
    CompressedFheInt208 cloned = new CompressedFheInt208();
    executeWithErrorHandling(() -> compressed_fhe_int208_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
