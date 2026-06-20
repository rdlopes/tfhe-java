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
public final class CompressedFheUint4 extends AbstractCompressedFheType<Byte, FheUint4, CompressedFheUint4> {

  static final Handles<Byte> H = new Handles<>(
      FheValueKind.BYTE,
      TfheHeader::compressed_fhe_uint4_decompress,
      TfheHeader::compressed_fhe_uint4_clone,
      TfheHeader::compressed_fhe_uint4_safe_serialize,
      TfheHeader::compressed_fhe_uint4_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_uint4_try_encrypt_with_client_key_u8,
      null);

  CompressedFheUint4() { super(TfheHeader::compressed_fhe_uint4_destroy); }

  @Override protected Handles<Byte>       handles()         { return H; }
  @Override protected FheUint4            newDecompressed() { return new FheUint4(); }
  @Override protected CompressedFheUint4  newInstance()     { return new CompressedFheUint4(); }

  public static CompressedFheUint4 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint4::new);
  }
  public static CompressedFheUint4 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint4::new);
  }
}
