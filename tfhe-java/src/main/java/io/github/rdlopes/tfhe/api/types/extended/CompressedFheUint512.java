package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.extended.U512;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/// Compressed encrypted unsigned 512-bit integer.
@Generated
public final class CompressedFheUint512 extends AbstractCompressedFheType<U512, FheUint512, CompressedFheUint512> {

  static final Handles<U512> H = new Handles<>(
    new FheValueKind.Wide<>(U512::newEmpty),
      TfheHeader::compressed_fhe_uint512_decompress,
      TfheHeader::compressed_fhe_uint512_clone,
      TfheHeader::compressed_fhe_uint512_safe_serialize,
      TfheHeader::compressed_fhe_uint512_safe_deserialize_conformant,
      null,
      TfheHeader::compressed_fhe_uint512_try_encrypt_with_client_key_u512);

  CompressedFheUint512() { super(TfheHeader::compressed_fhe_uint512_destroy); }

  @Override protected Handles<U512>         handles()         { return H; }
  @Override protected FheUint512            newDecompressed() { return new FheUint512(); }
  @Override protected CompressedFheUint512  newInstance()     { return new CompressedFheUint512(); }

  public static CompressedFheUint512 encrypt(U512 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint512::new);
  }
  public static CompressedFheUint512 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint512::new);
  }
}
