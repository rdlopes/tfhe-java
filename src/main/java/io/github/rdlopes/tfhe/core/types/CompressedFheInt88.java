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
    CompressedFheInt88 compressedFheInt88 = new CompressedFheInt88();
    executeWithErrorHandling(() -> compressed_fhe_int88_try_encrypt_with_client_key_i128(clearValue.getAddress(), clientKey.getValue(), compressedFheInt88.getAddress()));
    return compressedFheInt88;
  }

  public static CompressedFheInt88 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt88 compressedFheInt88 = new CompressedFheInt88();
    executeWithErrorHandling(() -> compressed_fhe_int88_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt88.getAddress()));
    return compressedFheInt88;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int88_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt88 decompress() {
    FheInt88 fheint88 = new FheInt88();
    executeWithErrorHandling(() -> compressed_fhe_int88_decompress(getValue(), fheint88.getAddress()));
    return fheint88;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt88 clone() {
    CompressedFheInt88 compressedFheInt88 = new CompressedFheInt88();
    executeWithErrorHandling(() -> compressed_fhe_int88_clone(getValue(), compressedFheInt88.getAddress()));
    return compressedFheInt88;
  }
}