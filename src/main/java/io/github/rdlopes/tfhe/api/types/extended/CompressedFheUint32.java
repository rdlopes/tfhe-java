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
public final class CompressedFheUint32 extends AbstractCompressedFheType<Integer, FheUint32, CompressedFheUint32> {

  static final Handles<Integer> H = new Handles<>(
      FheValueKind.INT,
      TfheHeader::compressed_fhe_uint32_decompress,
      TfheHeader::compressed_fhe_uint32_clone,
      TfheHeader::compressed_fhe_uint32_safe_serialize,
      TfheHeader::compressed_fhe_uint32_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_uint32_try_encrypt_with_client_key_u32,
      null);

  CompressedFheUint32() { super(TfheHeader::compressed_fhe_uint32_destroy); }

  @Override protected Handles<Integer>     handles()         { return H; }
  @Override protected FheUint32            newDecompressed() { return new FheUint32(); }
  @Override protected CompressedFheUint32  newInstance()     { return new CompressedFheUint32(); }

  public static CompressedFheUint32 encrypt(Integer clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint32::new);
  }
  public static CompressedFheUint32 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint32::new);
  }
}
