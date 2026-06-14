package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.U1024;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/// Compressed encrypted unsigned 1024-bit integer.
public final class CompressedFheUint1024 extends AbstractCompressedFheType<U1024, FheUint1024, CompressedFheUint1024> {

  static final Handles<U1024> H = new Handles<>(
    new FheValueKind.Wide<>(U1024::newEmpty),
      TfheHeader::compressed_fhe_uint1024_decompress,
      TfheHeader::compressed_fhe_uint1024_clone,
      TfheHeader::compressed_fhe_uint1024_safe_serialize,
      TfheHeader::compressed_fhe_uint1024_safe_deserialize_conformant,
      null,
      TfheHeader::compressed_fhe_uint1024_try_encrypt_with_client_key_u1024);

  CompressedFheUint1024() { super(TfheHeader::compressed_fhe_uint1024_destroy); }

  @Override protected Handles<U1024>         handles()         { return H; }
  @Override protected FheUint1024            newDecompressed() { return new FheUint1024(); }
  @Override protected CompressedFheUint1024  newInstance()     { return new CompressedFheUint1024(); }

  public static CompressedFheUint1024 encrypt(U1024 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint1024::new);
  }
  public static CompressedFheUint1024 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint1024::new);
  }
}
