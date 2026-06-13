package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class ShortintCompressedCiphertext extends NativePointer {

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
}
