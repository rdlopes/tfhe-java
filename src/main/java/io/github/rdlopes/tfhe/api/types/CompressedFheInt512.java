package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.I512;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/**
 * Compressed encrypted signed 512-bit integer.
 */
public final class CompressedFheInt512 extends AbstractCompressedFheType<I512, FheInt512, CompressedFheInt512> {

  static final Handles<I512> H = new Handles<>(
      new FheValueKind.Wide<>(I512::new),
      TfheHeader::compressed_fhe_int512_decompress,
      TfheHeader::compressed_fhe_int512_clone,
      TfheHeader::compressed_fhe_int512_safe_serialize,
      TfheHeader::compressed_fhe_int512_safe_deserialize_conformant,
      null,                                                           // encryptClientKey — N/A for Wide
      TfheHeader::compressed_fhe_int512_try_encrypt_with_client_key_i512);

  CompressedFheInt512() { super(TfheHeader::compressed_fhe_int512_destroy); }

  @Override protected Handles<I512>        handles()         { return H; }
  @Override protected FheInt512            newDecompressed() { return new FheInt512(); }
  @Override protected CompressedFheInt512  newInstance()     { return new CompressedFheInt512(); }

  public static CompressedFheInt512 encrypt(I512 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt512::new);
  }
  public static CompressedFheInt512 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt512::new);
  }
}
