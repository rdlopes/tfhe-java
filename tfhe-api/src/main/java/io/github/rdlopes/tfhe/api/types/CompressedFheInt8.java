package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.FheValueKind;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

/// Compressed encrypted signed 8-bit integer.
@Generated
public final class CompressedFheInt8 extends AbstractCompressedFheType<Byte, FheInt8, CompressedFheInt8> {

  static final Handles<Byte> H = new Handles<>(
      FheValueKind.BYTE,
      TfheHeader::compressed_fhe_int8_decompress,
      TfheHeader::compressed_fhe_int8_clone,
      TfheHeader::compressed_fhe_int8_safe_serialize,
      TfheHeader::compressed_fhe_int8_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_int8_try_encrypt_with_client_key_i8,
      null);   // encryptWideClientKey — N/A for Byte

  CompressedFheInt8() {
    super(TfheHeader::compressed_fhe_int8_destroy);
  }

  @Override protected Handles<Byte>  handles()        { return H; }
  @Override protected FheInt8        newDecompressed() { return new FheInt8(); }
  @Override protected CompressedFheInt8 newInstance() { return new CompressedFheInt8(); }

  public static CompressedFheInt8 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt8::new);
  }

  public static CompressedFheInt8 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt8::new);
  }
}
