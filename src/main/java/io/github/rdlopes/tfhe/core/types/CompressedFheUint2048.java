package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint2048 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint2048() {
    super(CompressedFheUint2048.class, TfheWrapper::compressed_fhe_uint2048_destroy);
  }

  public static CompressedFheUint2048 encryptWithClientKey(U2048 clearValue, ClientKey clientKey) {
    CompressedFheUint2048 compressed = new CompressedFheUint2048();
    executeWithErrorHandling(() -> compressed_fhe_uint2048_try_encrypt_with_client_key_u2048(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint2048 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint2048 compressed = new CompressedFheUint2048();
    executeWithErrorHandling(() -> compressed_fhe_uint2048_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint2048_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint2048 decompress() {
    FheUint2048 fhe = new FheUint2048();
    executeWithErrorHandling(() -> compressed_fhe_uint2048_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint2048 clone() {
    CompressedFheUint2048 cloned = new CompressedFheUint2048();
    executeWithErrorHandling(() -> compressed_fhe_uint2048_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
