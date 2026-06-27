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
public final class CompressedFheInt12 extends AbstractCompressedFheType<Short, FheInt12, CompressedFheInt12> {

  static final Handles<Short> H = new Handles<>(
      FheValueKind.SHORT,
      TfheHeader::compressed_fhe_int12_decompress,
      TfheHeader::compressed_fhe_int12_clone,
      TfheHeader::compressed_fhe_int12_safe_serialize,
      TfheHeader::compressed_fhe_int12_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_int12_try_encrypt_with_client_key_i16,
      null);

  CompressedFheInt12() { super(TfheHeader::compressed_fhe_int12_destroy); }

  @Override protected Handles<Short>       handles()         { return H; }
  @Override protected FheInt12             newDecompressed() { return new FheInt12(); }
  @Override protected CompressedFheInt12   newInstance()     { return new CompressedFheInt12(); }

  public static CompressedFheInt12 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt12::new);
  }
  public static CompressedFheInt12 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt12::new);
  }
}
