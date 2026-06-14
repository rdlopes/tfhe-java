package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/**
 * Compressed encrypted unsigned 8-bit integer.
 */
public final class CompressedFheUint8 extends AbstractCompressedFheType<Byte, FheUint8, CompressedFheUint8> {

  static final Handles<Byte> H = new Handles<>(
      FheValueKind.BYTE,
      TfheHeader::compressed_fhe_uint8_decompress,
      TfheHeader::compressed_fhe_uint8_clone,
      TfheHeader::compressed_fhe_uint8_safe_serialize,
      TfheHeader::compressed_fhe_uint8_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_uint8_try_encrypt_with_client_key_u8,
      null);

  CompressedFheUint8() {
    super(TfheHeader::compressed_fhe_uint8_destroy);
  }

  @Override protected Handles<Byte>     handles()         { return H; }
  @Override protected FheUint8          newDecompressed() { return new FheUint8(); }
  @Override protected CompressedFheUint8 newInstance()    { return new CompressedFheUint8(); }

  public static CompressedFheUint8 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint8::new);
  }

  public static CompressedFheUint8 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint8::new);
  }
}
