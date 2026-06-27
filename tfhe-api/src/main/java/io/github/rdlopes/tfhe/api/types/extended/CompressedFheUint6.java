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
public final class CompressedFheUint6 extends AbstractCompressedFheType<Byte, FheUint6, CompressedFheUint6> {

  static final Handles<Byte> H = new Handles<>(
      FheValueKind.BYTE,
      TfheHeader::compressed_fhe_uint6_decompress,
      TfheHeader::compressed_fhe_uint6_clone,
      TfheHeader::compressed_fhe_uint6_safe_serialize,
      TfheHeader::compressed_fhe_uint6_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_uint6_try_encrypt_with_client_key_u8,
      null);

  CompressedFheUint6() { super(TfheHeader::compressed_fhe_uint6_destroy); }

  @Override protected Handles<Byte>       handles()         { return H; }
  @Override protected FheUint6            newDecompressed() { return new FheUint6(); }
  @Override protected CompressedFheUint6  newInstance()     { return new CompressedFheUint6(); }

  public static CompressedFheUint6 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint6::new);
  }
  public static CompressedFheUint6 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint6::new);
  }
}
