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

/// Compressed encrypted signed 160-bit integer.
@Generated
public final class CompressedFheInt160 extends AbstractCompressedFheType<I256, FheInt160, CompressedFheInt160> {

  static final Handles<I256> H = new Handles<>(
    new FheValueKind.Wide<>(I256::newEmpty),
      TfheHeader::compressed_fhe_int160_decompress,
      TfheHeader::compressed_fhe_int160_clone,
      TfheHeader::compressed_fhe_int160_safe_serialize,
      TfheHeader::compressed_fhe_int160_safe_deserialize_conformant,
      null,                                                           // encryptClientKey — N/A for Wide
      TfheHeader::compressed_fhe_int160_try_encrypt_with_client_key_i256);

  CompressedFheInt160() { super(TfheHeader::compressed_fhe_int160_destroy); }

  @Override protected Handles<I256>        handles()         { return H; }
  @Override protected FheInt160            newDecompressed() { return new FheInt160(); }
  @Override protected CompressedFheInt160  newInstance()     { return new CompressedFheInt160(); }

  public static CompressedFheInt160 encrypt(I256 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt160::new);
  }
  public static CompressedFheInt160 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt160::new);
  }
}
