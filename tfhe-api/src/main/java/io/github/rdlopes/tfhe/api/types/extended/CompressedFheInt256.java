package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.extended.I256;
import io.github.rdlopes.tfhe.core.ffm.FheValueKind;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

/// Compressed encrypted signed 256-bit integer.
@Generated
public final class CompressedFheInt256 extends AbstractCompressedFheType<I256, FheInt256, CompressedFheInt256> {

  static final Handles<I256> H = new Handles<>(
    new FheValueKind.Wide<>(I256::newEmpty),
      TfheHeader::compressed_fhe_int256_decompress,
      TfheHeader::compressed_fhe_int256_clone,
      TfheHeader::compressed_fhe_int256_safe_serialize,
      TfheHeader::compressed_fhe_int256_safe_deserialize_conformant,
      null,                                                           // encryptClientKey — N/A for Wide
      TfheHeader::compressed_fhe_int256_try_encrypt_with_client_key_i256);

  CompressedFheInt256() { super(TfheHeader::compressed_fhe_int256_destroy); }

  @Override protected Handles<I256>        handles()         { return H; }
  @Override protected FheInt256            newDecompressed() { return new FheInt256(); }
  @Override protected CompressedFheInt256  newInstance()     { return new CompressedFheInt256(); }

  public static CompressedFheInt256 encrypt(I256 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt256::new);
  }
  public static CompressedFheInt256 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt256::new);
  }
}
