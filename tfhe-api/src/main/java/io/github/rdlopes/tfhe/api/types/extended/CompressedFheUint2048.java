package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.extended.U2048;
import io.github.rdlopes.tfhe.core.ffm.FheValueKind;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

/// Compressed encrypted unsigned 2048-bit integer.
@Generated
public final class CompressedFheUint2048 extends AbstractCompressedFheType<U2048, FheUint2048, CompressedFheUint2048> {

  static final Handles<U2048> H = new Handles<>(
    new FheValueKind.Wide<>(U2048::newEmpty),
      TfheHeader::compressed_fhe_uint2048_decompress,
      TfheHeader::compressed_fhe_uint2048_clone,
      TfheHeader::compressed_fhe_uint2048_safe_serialize,
      TfheHeader::compressed_fhe_uint2048_safe_deserialize_conformant,
      null,
      TfheHeader::compressed_fhe_uint2048_try_encrypt_with_client_key_u2048);

  CompressedFheUint2048() { super(TfheHeader::compressed_fhe_uint2048_destroy); }

  @Override protected Handles<U2048>         handles()         { return H; }
  @Override protected FheUint2048            newDecompressed() { return new FheUint2048(); }
  @Override protected CompressedFheUint2048  newInstance()     { return new CompressedFheUint2048(); }

  public static CompressedFheUint2048 encrypt(U2048 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint2048::new);
  }
  public static CompressedFheUint2048 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint2048::new);
  }
}
