package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint256 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint256() {
    super(CompressedFheUint256.class, TfheWrapper::compressed_fhe_uint256_destroy);
  }

  public static CompressedFheUint256 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint256 compressed = new CompressedFheUint256();
    executeWithErrorHandling(() -> compressed_fhe_uint256_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint256 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint256 compressed = new CompressedFheUint256();
    executeWithErrorHandling(() -> compressed_fhe_uint256_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint256_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint256 decompress() {
    FheUint256 fhe = new FheUint256();
    executeWithErrorHandling(() -> compressed_fhe_uint256_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint256 clone() {
    CompressedFheUint256 cloned = new CompressedFheUint256();
    executeWithErrorHandling(() -> compressed_fhe_uint256_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
