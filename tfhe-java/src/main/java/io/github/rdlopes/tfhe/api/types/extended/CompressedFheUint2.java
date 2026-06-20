package io.github.rdlopes.tfhe.api.types.extended;

import io.github.rdlopes.tfhe.api.types.*;
import io.github.rdlopes.tfhe.utils.Generated;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

@Generated
public final class CompressedFheUint2 extends AbstractCompressedFheType<Byte, FheUint2, CompressedFheUint2> {

  static final Handles<Byte> H = new Handles<>(
      FheValueKind.BYTE,
      TfheHeader::compressed_fhe_uint2_decompress,
      TfheHeader::compressed_fhe_uint2_clone,
      TfheHeader::compressed_fhe_uint2_safe_serialize,
      TfheHeader::compressed_fhe_uint2_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_uint2_try_encrypt_with_client_key_u8,
      null);

  CompressedFheUint2() { super(TfheHeader::compressed_fhe_uint2_destroy); }

  @Override protected Handles<Byte>       handles()         { return H; }
  @Override protected FheUint2            newDecompressed() { return new FheUint2(); }
  @Override protected CompressedFheUint2  newInstance()     { return new CompressedFheUint2(); }

  public static CompressedFheUint2 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint2::new);
  }
  public static CompressedFheUint2 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint2::new);
  }
}
