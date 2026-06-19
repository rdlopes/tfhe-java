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
public final class CompressedFheUint16 extends AbstractCompressedFheType<Short, FheUint16, CompressedFheUint16> {

  static final Handles<Short> H = new Handles<>(
      FheValueKind.SHORT,
      TfheHeader::compressed_fhe_uint16_decompress,
      TfheHeader::compressed_fhe_uint16_clone,
      TfheHeader::compressed_fhe_uint16_safe_serialize,
      TfheHeader::compressed_fhe_uint16_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_uint16_try_encrypt_with_client_key_u16,
      null);

  CompressedFheUint16() { super(TfheHeader::compressed_fhe_uint16_destroy); }

  @Override protected Handles<Short>       handles()         { return H; }
  @Override protected FheUint16            newDecompressed() { return new FheUint16(); }
  @Override protected CompressedFheUint16  newInstance()     { return new CompressedFheUint16(); }

  public static CompressedFheUint16 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint16::new);
  }
  public static CompressedFheUint16 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint16::new);
  }
}
