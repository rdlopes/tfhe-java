package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.I128;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/**
 * Compressed encrypted signed 128-bit integer.
 */
public final class CompressedFheInt128 extends AbstractCompressedFheType<I128, FheInt128, CompressedFheInt128> {

  static final Handles<I128> H = new Handles<>(
      new FheValueKind.Wide<>(I128::new),
      TfheHeader::compressed_fhe_int128_decompress,
      TfheHeader::compressed_fhe_int128_clone,
      TfheHeader::compressed_fhe_int128_safe_serialize,
      TfheHeader::compressed_fhe_int128_safe_deserialize_conformant,
      null,                                                           // encryptClientKey — N/A for Wide
      TfheHeader::compressed_fhe_int128_try_encrypt_with_client_key_i128);

  CompressedFheInt128() { super(TfheHeader::compressed_fhe_int128_destroy); }

  @Override protected Handles<I128>        handles()         { return H; }
  @Override protected FheInt128            newDecompressed() { return new FheInt128(); }
  @Override protected CompressedFheInt128  newInstance()     { return new CompressedFheInt128(); }

  public static CompressedFheInt128 encrypt(I128 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt128::new);
  }
  public static CompressedFheInt128 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt128::new);
  }
}
