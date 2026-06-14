package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.U128;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/// Compressed encrypted unsigned 128-bit integer.
public final class CompressedFheUint128 extends AbstractCompressedFheType<U128, FheUint128, CompressedFheUint128> {

  static final Handles<U128> H = new Handles<>(
    new FheValueKind.Wide<>(U128::newEmpty),
      TfheHeader::compressed_fhe_uint128_decompress,
      TfheHeader::compressed_fhe_uint128_clone,
      TfheHeader::compressed_fhe_uint128_safe_serialize,
      TfheHeader::compressed_fhe_uint128_safe_deserialize_conformant,
      null,
      TfheHeader::compressed_fhe_uint128_try_encrypt_with_client_key_u128);

  CompressedFheUint128() { super(TfheHeader::compressed_fhe_uint128_destroy); }

  @Override protected Handles<U128>         handles()         { return H; }
  @Override protected FheUint128            newDecompressed() { return new FheUint128(); }
  @Override protected CompressedFheUint128  newInstance()     { return new CompressedFheUint128(); }

  public static CompressedFheUint128 encrypt(U128 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint128::new);
  }
  public static CompressedFheUint128 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint128::new);
  }
}
