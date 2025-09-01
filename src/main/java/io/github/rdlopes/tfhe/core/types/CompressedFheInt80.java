package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt80 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt80() {
    super(CompressedFheInt80.class, TfheWrapper::compressed_fhe_int80_destroy);
  }

  public static CompressedFheInt80 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt80 compressedFheInt80 = new CompressedFheInt80();
    executeWithErrorHandling(() -> compressed_fhe_int80_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressedFheInt80.getAddress()));
    return compressedFheInt80;
  }

  public static CompressedFheInt80 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt80 compressedFheInt80 = new CompressedFheInt80();
    executeWithErrorHandling(() -> compressed_fhe_int80_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt80.getAddress()));
    return compressedFheInt80;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int80_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt80 decompress() {
    FheInt80 fheint80 = new FheInt80();
    executeWithErrorHandling(() -> compressed_fhe_int80_decompress(getValue(), fheint80.getAddress()));
    return fheint80;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt80 clone() {
    CompressedFheInt80 compressedFheInt80 = new CompressedFheInt80();
    executeWithErrorHandling(() -> compressed_fhe_int80_clone(getValue(), compressedFheInt80.getAddress()));
    return compressedFheInt80;
  }
}