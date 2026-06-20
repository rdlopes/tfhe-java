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
public final class CompressedFheInt14 extends AbstractCompressedFheType<Short, FheInt14, CompressedFheInt14> {

  static final Handles<Short> H = new Handles<>(
      FheValueKind.SHORT,
      TfheHeader::compressed_fhe_int14_decompress,
      TfheHeader::compressed_fhe_int14_clone,
      TfheHeader::compressed_fhe_int14_safe_serialize,
      TfheHeader::compressed_fhe_int14_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_int14_try_encrypt_with_client_key_i16,
      null);

  CompressedFheInt14() { super(TfheHeader::compressed_fhe_int14_destroy); }

  @Override protected Handles<Short>       handles()         { return H; }
  @Override protected FheInt14             newDecompressed() { return new FheInt14(); }
  @Override protected CompressedFheInt14   newInstance()     { return new CompressedFheInt14(); }

  public static CompressedFheInt14 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt14::new);
  }
  public static CompressedFheInt14 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt14::new);
  }
}
