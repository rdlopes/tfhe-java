package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint2 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint2() {
    super(CompressedFheUint2.class, TfheWrapper::compressed_fhe_uint2_destroy);
  }

  public static CompressedFheUint2 encryptWithClientKey(byte clearValue, ClientKey clientKey) {
    CompressedFheUint2 compressed = new CompressedFheUint2();
    executeWithErrorHandling(() -> compressed_fhe_uint2_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint2 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint2 compressed = new CompressedFheUint2();
    executeWithErrorHandling(() -> compressed_fhe_uint2_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint2_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint2 decompress() {
    FheUint2 fhe = new FheUint2();
    executeWithErrorHandling(() -> compressed_fhe_uint2_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint2 clone() {
    CompressedFheUint2 cloned = new CompressedFheUint2();
    executeWithErrorHandling(() -> compressed_fhe_uint2_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
