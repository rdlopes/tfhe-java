package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.NativePointer;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.*;

@SuppressWarnings({"java:S2975", "java:S1182"})
public class ShortintCompressedCiphertext extends NativePointer implements Cloneable {

  ShortintCompressedCiphertext() {
    super(TfheHeader::shortint_destroy_compressed_ciphertext);
  }

  public ShortintCiphertext decompress() {
    ShortintCiphertext result = new ShortintCiphertext();
    execute(() -> shortint_decompress_ciphertext(getValue(), result.getAddress()));
    return result;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> shortint_serialize_compressed_ciphertext(getValue(), dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  public static ShortintCompressedCiphertext deserialize(DynamicBuffer dynamicBuffer) {
    ShortintCompressedCiphertext deserialized = new ShortintCompressedCiphertext();
    execute(() -> shortint_deserialize_compressed_ciphertext(dynamicBuffer.getAddress(), deserialized.getAddress()));
    return deserialized;
  }

  /// Clones this compressed ciphertext.
  ///
  /// This operation performs a deep copy of the underlying compressed LWE ciphertext
  /// using the native C API function `shortint_compressed_ciphertext_clone`.
  ///
  /// @return a new independent `ShortintCompressedCiphertext` containing the same encrypted value
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ShortintCompressedCiphertext clone() {
    ShortintCompressedCiphertext result = new ShortintCompressedCiphertext();
    execute(() -> shortint_compressed_ciphertext_clone(getValue(), result.getAddress()));
    return result;
  }
}
