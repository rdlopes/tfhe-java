package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt12 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt12() {
    super(CompressedFheInt12.class, TfheWrapper::compressed_fhe_int12_destroy);
  }

  public static CompressedFheInt12 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    CompressedFheInt12 compressed = new CompressedFheInt12();
    executeWithErrorHandling(() -> compressed_fhe_int12_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt12 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt12 compressed = new CompressedFheInt12();
    executeWithErrorHandling(() -> compressed_fhe_int12_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int12_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt12 decompress() {
    FheInt12 fhe = new FheInt12();
    executeWithErrorHandling(() -> compressed_fhe_int12_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt12 clone() {
    CompressedFheInt12 cloned = new CompressedFheInt12();
    executeWithErrorHandling(() -> compressed_fhe_int12_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
