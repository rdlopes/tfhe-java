package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt10 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt10() {
    super(CompressedFheInt10.class, TfheWrapper::compressed_fhe_int10_destroy);
  }

  public static CompressedFheInt10 encryptWithClientKey(short clearValue, ClientKey clientKey) {
    CompressedFheInt10 compressed = new CompressedFheInt10();
    executeWithErrorHandling(() -> compressed_fhe_int10_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt10 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt10 compressed = new CompressedFheInt10();
    executeWithErrorHandling(() -> compressed_fhe_int10_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int10_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt10 decompress() {
    FheInt10 fhe = new FheInt10();
    executeWithErrorHandling(() -> compressed_fhe_int10_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt10 clone() {
    CompressedFheInt10 cloned = new CompressedFheInt10();
    executeWithErrorHandling(() -> compressed_fhe_int10_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
