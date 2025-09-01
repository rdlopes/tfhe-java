package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt256 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt256() {
    super(CompressedFheInt256.class, TfheWrapper::compressed_fhe_int256_destroy);
  }

  public static CompressedFheInt256 encryptWithClientKey(I256 clearValue, ClientKey clientKey) {
    CompressedFheInt256 compressedFheInt256 = new CompressedFheInt256();
    executeWithErrorHandling(() -> compressed_fhe_int256_try_encrypt_with_client_key_i256(clearValue.getAddress(), clientKey.getValue(), compressedFheInt256.getAddress()));
    return compressedFheInt256;
  }

  public static CompressedFheInt256 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt256 compressedFheInt256 = new CompressedFheInt256();
    executeWithErrorHandling(() -> compressed_fhe_int256_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt256.getAddress()));
    return compressedFheInt256;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int256_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt256 decompress() {
    FheInt256 fheint256 = new FheInt256();
    executeWithErrorHandling(() -> compressed_fhe_int256_decompress(getValue(), fheint256.getAddress()));
    return fheint256;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt256 clone() {
    CompressedFheInt256 compressedFheInt256 = new CompressedFheInt256();
    executeWithErrorHandling(() -> compressed_fhe_int256_clone(getValue(), compressedFheInt256.getAddress()));
    return compressedFheInt256;
  }
}