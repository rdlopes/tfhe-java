package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt96 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt96() {
    super(CompressedFheInt96.class, TfheWrapper::compressed_fhe_int96_destroy);
  }

  public static CompressedFheInt96 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt96 compressed = new CompressedFheInt96();
    executeWithErrorHandling(() -> compressed_fhe_int96_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt96 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt96 compressed = new CompressedFheInt96();
    executeWithErrorHandling(() -> compressed_fhe_int96_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int96_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt96 decompress() {
    FheInt96 fhe = new FheInt96();
    executeWithErrorHandling(() -> compressed_fhe_int96_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt96 clone() {
    CompressedFheInt96 cloned = new CompressedFheInt96();
    executeWithErrorHandling(() -> compressed_fhe_int96_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
