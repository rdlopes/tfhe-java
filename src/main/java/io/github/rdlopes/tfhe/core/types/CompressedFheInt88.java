package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt88 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt88() {
    super(CompressedFheInt88.class, TfheWrapper::compressed_fhe_int88_destroy);
  }

  public static CompressedFheInt88 encryptWithClientKey(I128 clearValue, ClientKey clientKey) {
    CompressedFheInt88 compressed = new CompressedFheInt88();
    executeWithErrorHandling(() -> compressed_fhe_int88_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public static CompressedFheInt88 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt88 compressed = new CompressedFheInt88();
    executeWithErrorHandling(() -> compressed_fhe_int88_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressed.getAddress()));
    return compressed;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int88_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt88 decompress() {
    FheInt88 fhe = new FheInt88();
    executeWithErrorHandling(() -> compressed_fhe_int88_decompress(getValue(), fhe.getAddress()));
    return fhe;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt88 clone() {
    CompressedFheInt88 cloned = new CompressedFheInt88();
    executeWithErrorHandling(() -> compressed_fhe_int88_clone(getValue(), cloned.getAddress()));
    return cloned;
  }
}
