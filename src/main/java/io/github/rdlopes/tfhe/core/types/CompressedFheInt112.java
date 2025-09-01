package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt112 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt112() {
    super(CompressedFheInt112.class, TfheWrapper::compressed_fhe_int112_destroy);
  }

  public static CompressedFheInt112 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt112 compressedFheInt112 = new CompressedFheInt112();
    executeWithErrorHandling(() -> compressed_fhe_int112_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressedFheInt112.getAddress()));
    return compressedFheInt112;
  }

  public static CompressedFheInt112 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt112 compressedFheInt112 = new CompressedFheInt112();
    executeWithErrorHandling(() -> compressed_fhe_int112_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt112.getAddress()));
    return compressedFheInt112;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int112_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt112 decompress() {
    FheInt112 fheint112 = new FheInt112();
    executeWithErrorHandling(() -> compressed_fhe_int112_decompress(getValue(), fheint112.getAddress()));
    return fheint112;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt112 clone() {
    CompressedFheInt112 compressedFheInt112 = new CompressedFheInt112();
    executeWithErrorHandling(() -> compressed_fhe_int112_clone(getValue(), compressedFheInt112.getAddress()));
    return compressedFheInt112;
  }
}