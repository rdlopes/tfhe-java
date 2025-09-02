package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint512 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint512() {
    super(CompressedFheUint512.class, TfheWrapper::compressed_fhe_uint512_destroy);
  }

  public static CompressedFheUint512 encryptWithClientKey(U512 clearValue, ClientKey clientKey) {
    CompressedFheUint512 compressed = new CompressedFheUint512();
    executeWithErrorHandling(() -> compressed_fhe_uint512_try_encrypt_with_client_key_u512(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint512 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint512 compressed = new CompressedFheUint512();
    executeWithErrorHandling(() -> compressed_fhe_uint512_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint512_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint512 decompress() {
    FheUint512 fhe = new FheUint512();
    executeWithErrorHandling(() -> compressed_fhe_uint512_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint512 clone() {
    CompressedFheUint512 cloned = new CompressedFheUint512();
    executeWithErrorHandling(() -> compressed_fhe_uint512_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
