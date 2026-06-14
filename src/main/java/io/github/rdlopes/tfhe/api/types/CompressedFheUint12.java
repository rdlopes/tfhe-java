package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

public final class CompressedFheUint12 extends AbstractCompressedFheType<Short, FheUint12, CompressedFheUint12> {

  static final Handles<Short> H = new Handles<>(
      FheValueKind.SHORT,
      TfheHeader::compressed_fhe_uint12_decompress,
      TfheHeader::compressed_fhe_uint12_clone,
      TfheHeader::compressed_fhe_uint12_safe_serialize,
      TfheHeader::compressed_fhe_uint12_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_uint12_try_encrypt_with_client_key_u16,
      null);

  CompressedFheUint12() { super(TfheHeader::compressed_fhe_uint12_destroy); }

  @Override protected Handles<Short>       handles()         { return H; }
  @Override protected FheUint12            newDecompressed() { return new FheUint12(); }
  @Override protected CompressedFheUint12  newInstance()     { return new CompressedFheUint12(); }

  public static CompressedFheUint12 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint12::new);
  }
  public static CompressedFheUint12 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint12::new);
  }
}
