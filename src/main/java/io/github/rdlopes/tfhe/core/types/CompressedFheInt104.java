package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt104 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt104() {
    super(CompressedFheInt104.class, TfheWrapper::compressed_fhe_int104_destroy);
  }

  public static CompressedFheInt104 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt104 compressed = new CompressedFheInt104();
    executeWithErrorHandling(() -> compressed_fhe_int104_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt104 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt104 compressed = new CompressedFheInt104();
    executeWithErrorHandling(() -> compressed_fhe_int104_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int104_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt104 decompress() {
    FheInt104 fhe = new FheInt104();
    executeWithErrorHandling(() -> compressed_fhe_int104_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt104 clone() {
    CompressedFheInt104 cloned = new CompressedFheInt104();
    executeWithErrorHandling(() -> compressed_fhe_int104_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
