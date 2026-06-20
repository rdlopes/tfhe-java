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
public final class CompressedFheInt10 extends AbstractCompressedFheType<Short, FheInt10, CompressedFheInt10> {

  static final Handles<Short> H = new Handles<>(
      FheValueKind.SHORT,
      TfheHeader::compressed_fhe_int10_decompress,
      TfheHeader::compressed_fhe_int10_clone,
      TfheHeader::compressed_fhe_int10_safe_serialize,
      TfheHeader::compressed_fhe_int10_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_int10_try_encrypt_with_client_key_i16,
      null);

  CompressedFheInt10() { super(TfheHeader::compressed_fhe_int10_destroy); }

  @Override protected Handles<Short>       handles()         { return H; }
  @Override protected FheInt10             newDecompressed() { return new FheInt10(); }
  @Override protected CompressedFheInt10   newInstance()     { return new CompressedFheInt10(); }

  public static CompressedFheInt10 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt10::new);
  }
  public static CompressedFheInt10 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt10::new);
  }
}
