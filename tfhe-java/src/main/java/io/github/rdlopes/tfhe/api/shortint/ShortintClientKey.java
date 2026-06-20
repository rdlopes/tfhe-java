package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.api.keys.CustomParameters;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.NativeCall.executeAndReturn;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class ShortintClientKey extends NativePointer {

  private ShortintClientKey() {
    super(TfheHeader::shortint_destroy_client_key);
  }

  public static ShortintClientKey generate(CustomParameters parameters) {
    ShortintClientKey clientKey = new ShortintClientKey();
    execute(() -> shortint_gen_client_key(parameters.address(), clientKey.getAddress()));
    return clientKey;
  }

  public static class KeyPair {
    public final ShortintClientKey clientKey;
    public final ShortintServerKey serverKey;

    public KeyPair(ShortintClientKey clientKey, ShortintServerKey serverKey) {
      this.clientKey = clientKey;
      this.serverKey = serverKey;
    }
  }

  public static KeyPair generateKeysWithParameters(CustomParameters parameters) {
    ShortintClientKey clientKey = new ShortintClientKey();
    ShortintServerKey serverKey = new ShortintServerKey();
    execute(() -> shortint_gen_keys_with_parameters(parameters.address(), clientKey.getAddress(), serverKey.getAddress()));
    return new KeyPair(clientKey, serverKey);
  }

  public static ShortintClientKey deserialize(DynamicBuffer dynamicBuffer) {
    ShortintClientKey clientKey = new ShortintClientKey();
    execute(() -> shortint_deserialize_client_key(dynamicBuffer.getAddress(), clientKey.getAddress()));
    return clientKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> shortint_serialize_client_key(getValue(), dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  public ShortintCiphertext encrypt(long value) {
    ShortintCiphertext ciphertext = new ShortintCiphertext();
    execute(() -> shortint_client_key_encrypt(getValue(), value, ciphertext.getAddress()));
    return ciphertext;
  }

  public ShortintCompressedCiphertext encryptCompressed(long value) {
    ShortintCompressedCiphertext ciphertext = new ShortintCompressedCiphertext();
    execute(() -> shortint_client_key_encrypt_compressed(getValue(), value, ciphertext.getAddress()));
    return ciphertext;
  }

  public long decrypt(ShortintCiphertext ciphertext) {
    return executeAndReturn(Long.class, address -> shortint_client_key_decrypt(getValue(), ciphertext.getValue(), address));
  }

  public ShortintCompressedPublicKey genCompressedPublicKey() {
    return ShortintCompressedPublicKey.generate(this);
  }

  public ShortintCompressedServerKey genCompressedServerKey() {
    return ShortintCompressedServerKey.generate(this);
  }
}
