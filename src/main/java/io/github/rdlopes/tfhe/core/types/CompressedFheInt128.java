package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt128 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt128() {
    super(CompressedFheInt128.class, TfheWrapper::compressed_fhe_int128_destroy);
  }

  public static CompressedFheInt128 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt128 compressedFheInt128 = new CompressedFheInt128();
    executeWithErrorHandling(() -> compressed_fhe_int128_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressedFheInt128.getAddress()));
    return compressedFheInt128;
  }

  public static CompressedFheInt128 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt128 compressedFheInt128 = new CompressedFheInt128();
    executeWithErrorHandling(() -> compressed_fhe_int128_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt128.getAddress()));
    return compressedFheInt128;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int128_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt128 decompress() {
    FheInt128 fheint128 = new FheInt128();
    executeWithErrorHandling(() -> compressed_fhe_int128_decompress(getValue(), fheint128.getAddress()));
    return fheint128;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt128 clone() {
    CompressedFheInt128 compressedFheInt128 = new CompressedFheInt128();
    executeWithErrorHandling(() -> compressed_fhe_int128_clone(getValue(), compressedFheInt128.getAddress()));
    return compressedFheInt128;
  }
}