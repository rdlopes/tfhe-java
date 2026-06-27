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
public final class CompressedFheInt16 extends AbstractCompressedFheType<Short, FheInt16, CompressedFheInt16> {

  static final Handles<Short> H = new Handles<>(
      FheValueKind.SHORT,
      TfheHeader::compressed_fhe_int16_decompress,
      TfheHeader::compressed_fhe_int16_clone,
      TfheHeader::compressed_fhe_int16_safe_serialize,
      TfheHeader::compressed_fhe_int16_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_int16_try_encrypt_with_client_key_i16,
      null);

  CompressedFheInt16() { super(TfheHeader::compressed_fhe_int16_destroy); }

  @Override protected Handles<Short>       handles()         { return H; }
  @Override protected FheInt16             newDecompressed() { return new FheInt16(); }
  @Override protected CompressedFheInt16   newInstance()     { return new CompressedFheInt16(); }

  public static CompressedFheInt16 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt16::new);
  }
  public static CompressedFheInt16 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt16::new);
  }
}
