package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheInt2048 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheInt2048() {
    super(CompressedFheInt2048.class, TfheWrapper::compressed_fhe_int2048_destroy);
  }

  public static CompressedFheInt2048 encryptWithClientKey(I2048 clearValue, ClientKey clientKey) {
    CompressedFheInt2048 compressedFheInt2048 = new CompressedFheInt2048();
    executeWithErrorHandling(() -> compressed_fhe_int2048_try_encrypt_with_client_key_i2048(clearValue.getAddress(), clientKey.getValue(), compressedFheInt2048.getAddress()));
    return compressedFheInt2048;
  }

  public static CompressedFheInt2048 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheInt2048 compressedFheInt2048 = new CompressedFheInt2048();
    executeWithErrorHandling(() -> compressed_fhe_int2048_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheInt2048.getAddress()));
    return compressedFheInt2048;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_int2048_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheInt2048 decompress() {
    FheInt2048 fheInt2048 = new FheInt2048();
    executeWithErrorHandling(() -> compressed_fhe_int2048_decompress(getValue(), fheInt2048.getAddress()));
    return fheInt2048;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt2048 clone() {
    CompressedFheInt2048 compressedFheInt2048 = new CompressedFheInt2048();
    executeWithErrorHandling(() -> compressed_fhe_int2048_clone(getValue(), compressedFheInt2048.getAddress()));
    return compressedFheInt2048;
  }

}
