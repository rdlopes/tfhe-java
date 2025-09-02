package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint200 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint200() {
    super(CompressedFheUint200.class, TfheWrapper::compressed_fhe_uint200_destroy);
  }

  public static CompressedFheUint200 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint200 compressed = new CompressedFheUint200();
    executeWithErrorHandling(() -> compressed_fhe_uint200_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint200 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint200 compressed = new CompressedFheUint200();
    executeWithErrorHandling(() -> compressed_fhe_uint200_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint200_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint200 decompress() {
    FheUint200 fhe = new FheUint200();
    executeWithErrorHandling(() -> compressed_fhe_uint200_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint200 clone() {
    CompressedFheUint200 cloned = new CompressedFheUint200();
    executeWithErrorHandling(() -> compressed_fhe_uint200_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
