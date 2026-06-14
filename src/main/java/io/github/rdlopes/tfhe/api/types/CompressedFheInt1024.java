package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.I1024;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/**
 * Compressed encrypted signed 1024-bit integer.
 */
public final class CompressedFheInt1024 extends AbstractCompressedFheType<I1024, FheInt1024, CompressedFheInt1024> {

  static final Handles<I1024> H = new Handles<>(
      new FheValueKind.Wide<>(I1024::new),
      TfheHeader::compressed_fhe_int1024_decompress,
      TfheHeader::compressed_fhe_int1024_clone,
      TfheHeader::compressed_fhe_int1024_safe_serialize,
      TfheHeader::compressed_fhe_int1024_safe_deserialize_conformant,
      null,                                                            // encryptClientKey — N/A for Wide
      TfheHeader::compressed_fhe_int1024_try_encrypt_with_client_key_i1024);

  CompressedFheInt1024() { super(TfheHeader::compressed_fhe_int1024_destroy); }

  @Override protected Handles<I1024>        handles()         { return H; }
  @Override protected FheInt1024            newDecompressed() { return new FheInt1024(); }
  @Override protected CompressedFheInt1024  newInstance()     { return new CompressedFheInt1024(); }

  public static CompressedFheInt1024 encrypt(I1024 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt1024::new);
  }
  public static CompressedFheInt1024 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt1024::new);
  }
}
