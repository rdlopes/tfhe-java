package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

public final class CompressedFheInt64 extends AbstractCompressedFheType<Long, FheInt64, CompressedFheInt64> {

  static final Handles<Long> H = new Handles<>(
      FheValueKind.LONG,
      TfheHeader::compressed_fhe_int64_decompress,
      TfheHeader::compressed_fhe_int64_clone,
      TfheHeader::compressed_fhe_int64_safe_serialize,
      TfheHeader::compressed_fhe_int64_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_int64_try_encrypt_with_client_key_i64,
      null);

  CompressedFheInt64() { super(TfheHeader::compressed_fhe_int64_destroy); }

  @Override protected Handles<Long>        handles()         { return H; }
  @Override protected FheInt64             newDecompressed() { return new FheInt64(); }
  @Override protected CompressedFheInt64   newInstance()     { return new CompressedFheInt64(); }

  public static CompressedFheInt64 encrypt(Long clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt64::new);
  }
  public static CompressedFheInt64 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt64::new);
  }
}
