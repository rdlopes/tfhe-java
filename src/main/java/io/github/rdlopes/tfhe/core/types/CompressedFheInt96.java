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
    CompressedFheInt96 compressedFheInt96 = new CompressedFheInt96();
    executeWithErrorHandling(() -> compressed_fhe_int96_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressedFheInt96.getAddress()));
    return compressedFheInt96;
  }

  public static CompressedFheInt96 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt96 compressedFheInt96 = new CompressedFheInt96();
    executeWithErrorHandling(() -> compressed_fhe_int96_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt96.getAddress()));
    return compressedFheInt96;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int96_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt96 decompress() {
    FheInt96 fheint96 = new FheInt96();
    executeWithErrorHandling(() -> compressed_fhe_int96_decompress(getValue(), fheint96.getAddress()));
    return fheint96;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt96 clone() {
    CompressedFheInt96 compressedFheInt96 = new CompressedFheInt96();
    executeWithErrorHandling(() -> compressed_fhe_int96_clone(getValue(), compressedFheInt96.getAddress()));
    return compressedFheInt96;
  }
}