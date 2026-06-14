package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

public final class CompressedFheInt32 extends AbstractCompressedFheType<Integer, FheInt32, CompressedFheInt32> {

  static final Handles<Integer> H = new Handles<>(
      FheValueKind.INT,
      TfheHeader::compressed_fhe_int32_decompress,
      TfheHeader::compressed_fhe_int32_clone,
      TfheHeader::compressed_fhe_int32_safe_serialize,
      TfheHeader::compressed_fhe_int32_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_int32_try_encrypt_with_client_key_i32,
      null);

  CompressedFheInt32() { super(TfheHeader::compressed_fhe_int32_destroy); }

  @Override protected Handles<Integer>     handles()         { return H; }
  @Override protected FheInt32             newDecompressed() { return new FheInt32(); }
  @Override protected CompressedFheInt32   newInstance()     { return new CompressedFheInt32(); }

  public static CompressedFheInt32 encrypt(Integer clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt32::new);
  }
  public static CompressedFheInt32 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt32::new);
  }
}
