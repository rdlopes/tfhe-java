package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.NativePointer;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.*;

public class ShortintPublicKey extends NativePointer {

  ShortintPublicKey() {
    super(TfheHeader::shortint_destroy_public_key);
  }

  public static ShortintPublicKey generate(ShortintClientKey clientKey) {
    ShortintPublicKey publicKey = new ShortintPublicKey();
    execute(() -> shortint_gen_public_key(clientKey.getValue(), publicKey.getAddress()));
    return publicKey;
  }

  public static ShortintPublicKey deserialize(DynamicBuffer dynamicBuffer) {
    ShortintPublicKey publicKey = new ShortintPublicKey();
    execute(() -> shortint_deserialize_public_key(dynamicBuffer.getAddress(), publicKey.getAddress()));
    return publicKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> shortint_serialize_public_key(getValue(), dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  public ShortintCiphertext encrypt(long value) {
    ShortintCiphertext ciphertext = new ShortintCiphertext();
    execute(() -> shortint_public_key_encrypt(getValue(), value, ciphertext.getAddress()));
    return ciphertext;
  }
}
