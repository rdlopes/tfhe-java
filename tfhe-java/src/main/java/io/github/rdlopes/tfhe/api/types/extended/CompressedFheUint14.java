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
public final class CompressedFheUint14 extends AbstractCompressedFheType<Short, FheUint14, CompressedFheUint14> {

  static final Handles<Short> H = new Handles<>(
      FheValueKind.SHORT,
      TfheHeader::compressed_fhe_uint14_decompress,
      TfheHeader::compressed_fhe_uint14_clone,
      TfheHeader::compressed_fhe_uint14_safe_serialize,
      TfheHeader::compressed_fhe_uint14_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_uint14_try_encrypt_with_client_key_u16,
      null);

  CompressedFheUint14() { super(TfheHeader::compressed_fhe_uint14_destroy); }

  @Override protected Handles<Short>       handles()         { return H; }
  @Override protected FheUint14            newDecompressed() { return new FheUint14(); }
  @Override protected CompressedFheUint14  newInstance()     { return new CompressedFheUint14(); }

  public static CompressedFheUint14 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint14::new);
  }
  public static CompressedFheUint14 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint14::new);
  }
}
