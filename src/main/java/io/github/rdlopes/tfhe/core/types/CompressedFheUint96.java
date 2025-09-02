package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint96 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint96() {
    super(CompressedFheUint96.class, TfheWrapper::compressed_fhe_uint96_destroy);
  }

  public static CompressedFheUint96 encryptWithClientKey(U128 clearValue, ClientKey clientKey) {
    CompressedFheUint96 compressed = new CompressedFheUint96();
    executeWithErrorHandling(() -> compressed_fhe_uint96_try_encrypt_with_client_key_u128(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint96 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint96 compressed = new CompressedFheUint96();
    executeWithErrorHandling(() -> compressed_fhe_uint96_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint96_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint96 decompress() {
    FheUint96 fhe = new FheUint96();
    executeWithErrorHandling(() -> compressed_fhe_uint96_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint96 clone() {
    CompressedFheUint96 cloned = new CompressedFheUint96();
    executeWithErrorHandling(() -> compressed_fhe_uint96_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
