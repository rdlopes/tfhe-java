package io.github.rdlopes.tfhe.core.types;

import io.github.rdlopes.tfhe.core.keys.ClientKey;
import io.github.rdlopes.tfhe.core.keys.ServerKey;
import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class CompressedFheBool extends AddressLayoutPointer implements Cloneable {

  protected CompressedFheBool() {
    super(address -> compressed_fhe_bool_destroy(address.get(C_POINTER, 0)));
  }

  public static CompressedFheBool encryptWithClientKey(boolean clearValue, ClientKey clientKey) {
    CompressedFheBool compressedFheBool = new CompressedFheBool();
    executeWithErrorHandling(() -> compressed_fhe_bool_try_encrypt_with_client_key_bool(clearValue, clientKey.getValue(), compressedFheBool.getAddress()));
    return compressedFheBool;
  }

  public static CompressedFheBool deserialize(DynamicBufferView bufferView, ServerKey serverKey) {
    CompressedFheBool compressedFheBool = new CompressedFheBool();
    executeWithErrorHandling(() -> compressed_fhe_bool_safe_deserialize_conformant(bufferView.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), compressedFheBool.getAddress()));
    return compressedFheBool;
  }

  public DynamicBufferView serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    executeWithErrorHandling(() -> compressed_fhe_bool_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));
    return dynamicBuffer.view();
  }

  public FheBool decompress() {
    FheBool fheBool = new FheBool();
    executeWithErrorHandling(() -> compressed_fhe_bool_decompress(getValue(), fheBool.getAddress()));
    return fheBool;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheBool clone() {
    CompressedFheBool compressedFheBool = new CompressedFheBool();
    executeWithErrorHandling(() -> compressed_fhe_bool_clone(getValue(), compressedFheBool.getAddress()));
    return compressedFheBool;
  }
}
