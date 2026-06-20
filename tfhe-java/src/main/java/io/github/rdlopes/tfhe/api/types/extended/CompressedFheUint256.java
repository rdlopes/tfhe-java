package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.extended.U256;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/// Compressed encrypted unsigned 256-bit integer.
@Generated
public final class CompressedFheUint256 extends AbstractCompressedFheType<U256, FheUint256, CompressedFheUint256> {

  static final Handles<U256> H = new Handles<>(
    new FheValueKind.Wide<>(U256::newEmpty),
      TfheHeader::compressed_fhe_uint256_decompress,
      TfheHeader::compressed_fhe_uint256_clone,
      TfheHeader::compressed_fhe_uint256_safe_serialize,
      TfheHeader::compressed_fhe_uint256_safe_deserialize_conformant,
      null,
      TfheHeader::compressed_fhe_uint256_try_encrypt_with_client_key_u256);

  CompressedFheUint256() { super(TfheHeader::compressed_fhe_uint256_destroy); }

  @Override protected Handles<U256>         handles()         { return H; }
  @Override protected FheUint256            newDecompressed() { return new FheUint256(); }
  @Override protected CompressedFheUint256  newInstance()     { return new CompressedFheUint256(); }

  public static CompressedFheUint256 encrypt(U256 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint256::new);
  }
  public static CompressedFheUint256 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint256::new);
  }
}
