package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint208 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint208() {
    super(CompressedFheUint208.class, TfheWrapper::compressed_fhe_uint208_destroy);
  }

  public static CompressedFheUint208 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint208 compressed = new CompressedFheUint208();
    executeWithErrorHandling(() -> compressed_fhe_uint208_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint208 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint208 compressed = new CompressedFheUint208();
    executeWithErrorHandling(() -> compressed_fhe_uint208_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint208_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint208 decompress() {
    FheUint208 fhe = new FheUint208();
    executeWithErrorHandling(() -> compressed_fhe_uint208_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint208 clone() {
    CompressedFheUint208 cloned = new CompressedFheUint208();
    executeWithErrorHandling(() -> compressed_fhe_uint208_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
