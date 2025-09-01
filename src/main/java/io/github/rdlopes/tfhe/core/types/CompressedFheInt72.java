package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt72 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt72() {
    super(CompressedFheInt72.class, TfheWrapper::compressed_fhe_int72_destroy);
  }

  public static CompressedFheInt72 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt72 compressedFheInt72 = new CompressedFheInt72();
    executeWithErrorHandling(() -> compressed_fhe_int72_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressedFheInt72.getAddress()));
    return compressedFheInt72;
  }

  public static CompressedFheInt72 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt72 compressedFheInt72 = new CompressedFheInt72();
    executeWithErrorHandling(() -> compressed_fhe_int72_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt72.getAddress()));
    return compressedFheInt72;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int72_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt72 decompress() {
    FheInt72 fheint72 = new FheInt72();
    executeWithErrorHandling(() -> compressed_fhe_int72_decompress(getValue(), fheint72.getAddress()));
    return fheint72;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt72 clone() {
    CompressedFheInt72 compressedFheInt72 = new CompressedFheInt72();
    executeWithErrorHandling(() -> compressed_fhe_int72_clone(getValue(), compressedFheInt72.getAddress()));
    return compressedFheInt72;
  }
}