package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;
import io.github.rdlopes.tfhe.ffm.TfheWrapper;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheUint160 extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheUint160() {
    super(CompressedFheUint160.class, TfheWrapper::compressed_fhe_uint160_destroy);
  }

  public static CompressedFheUint160 encryptWithClientKey(U256 clearValue, ClientKey clientKey) {
    CompressedFheUint160 compressedFheUint160 = new CompressedFheUint160();
    executeWithErrorHandling(() -> compressed_fhe_uint160_try_encrypt_with_client_key_u256(clearValue.getAddress(), clientKey.getValue(), compressedFheUint160.getAddress()));
    return compressedFheUint160;
  }

  public static CompressedFheUint160 deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheUint160 compressedFheUint160 = new CompressedFheUint160();
    executeWithErrorHandling(() -> compressed_fhe_uint160_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheUint160.getAddress()));
    return compressedFheUint160;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_uint160_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    DynamicBufferView dynamicBufferView = dynamicBuffer.view();
    dynamicBuffer.destroy();
    return dynamicBufferView;
  }

  public FheUint160 decompress() {
    FheUint160 fheuint160 = new FheUint160();
    executeWithErrorHandling(() -> compressed_fhe_uint160_decompress(getValue(), fheuint160.getAddress()));
    return fheuint160;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint160 clone() {
    CompressedFheUint160 compressedFheUint160 = new CompressedFheUint160();
    executeWithErrorHandling(() -> compressed_fhe_uint160_clone(getValue(), compressedFheUint160.getAddress()));
    return compressedFheUint160;
  }
}