package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class ShortintCompressedPublicKey extends NativePointer {

  private ShortintCompressedPublicKey() {
    super(TfheHeader::shortint_destroy_compressed_public_key);
  }

  public static ShortintCompressedPublicKey generate(ShortintClientKey clientKey) {
    ShortintCompressedPublicKey publicKey = new ShortintCompressedPublicKey();
    execute(() -> shortint_gen_compressed_public_key(clientKey.getValue(), publicKey.getAddress()));
    return publicKey;
  }

  public static ShortintCompressedPublicKey deserialize(DynamicBuffer dynamicBuffer) {
    ShortintCompressedPublicKey publicKey = new ShortintCompressedPublicKey();
    execute(() -> shortint_deserialize_compressed_public_key(dynamicBuffer.getAddress(), publicKey.getAddress()));
    return publicKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> shortint_serialize_compressed_public_key(getValue(), dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  public ShortintPublicKey decompress() {
    ShortintPublicKey result = new ShortintPublicKey();
    execute(() -> shortint_decompress_public_key(getValue(), result.getAddress()));
    return result;
  }

  public ShortintCiphertext encrypt(long value) {
    ShortintCiphertext ciphertext = new ShortintCiphertext();
    execute(() -> shortint_compressed_public_key_encrypt(getValue(), value, ciphertext.getAddress()));
    return ciphertext;
  }
}
