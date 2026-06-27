package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.core.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.FheValueKind;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

@Generated
public final class CompressedFheUint64 extends AbstractCompressedFheType<Long, FheUint64, CompressedFheUint64> {

  static final Handles<Long> H = new Handles<>(
      FheValueKind.LONG,
      TfheHeader::compressed_fhe_uint64_decompress,
      TfheHeader::compressed_fhe_uint64_clone,
      TfheHeader::compressed_fhe_uint64_safe_serialize,
      TfheHeader::compressed_fhe_uint64_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_uint64_try_encrypt_with_client_key_u64,
      null);

  CompressedFheUint64() { super(TfheHeader::compressed_fhe_uint64_destroy); }

  @Override protected Handles<Long>        handles()         { return H; }
  @Override protected FheUint64            newDecompressed() { return new FheUint64(); }
  @Override protected CompressedFheUint64  newInstance()     { return new CompressedFheUint64(); }

  public static CompressedFheUint64 encrypt(Long clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint64::new);
  }
  public static CompressedFheUint64 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint64::new);
  }
}
