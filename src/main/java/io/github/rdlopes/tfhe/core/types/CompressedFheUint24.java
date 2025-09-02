package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint24 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint24() {
    super(CompressedFheUint24.class, TfheWrapper::compressed_fhe_uint24_destroy);
  }

  public static CompressedFheUint24 encryptWithClientKey(int clearValue, ClientKey clientKey) {
    CompressedFheUint24 compressed = new CompressedFheUint24();
    executeWithErrorHandling(() -> compressed_fhe_uint24_try_encrypt_with_client_key_u32(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheUint24 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint24 compressed = new CompressedFheUint24();
    executeWithErrorHandling(() -> compressed_fhe_uint24_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint24_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint24 decompress() {
    FheUint24 fhe = new FheUint24();
    executeWithErrorHandling(() -> compressed_fhe_uint24_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint24 clone() {
    CompressedFheUint24 cloned = new CompressedFheUint24();
    executeWithErrorHandling(() -> compressed_fhe_uint24_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
