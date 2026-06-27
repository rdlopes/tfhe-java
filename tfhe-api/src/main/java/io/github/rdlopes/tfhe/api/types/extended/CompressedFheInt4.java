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
public final class CompressedFheInt4 extends AbstractCompressedFheType<Byte, FheInt4, CompressedFheInt4> {

  static final Handles<Byte> H = new Handles<>(
      FheValueKind.BYTE,
      TfheHeader::compressed_fhe_int4_decompress,
      TfheHeader::compressed_fhe_int4_clone,
      TfheHeader::compressed_fhe_int4_safe_serialize,
      TfheHeader::compressed_fhe_int4_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_int4_try_encrypt_with_client_key_i8,
      null);

  CompressedFheInt4() { super(TfheHeader::compressed_fhe_int4_destroy); }

  @Override protected Handles<Byte>       handles()         { return H; }
  @Override protected FheInt4             newDecompressed() { return new FheInt4(); }
  @Override protected CompressedFheInt4   newInstance()     { return new CompressedFheInt4(); }

  public static CompressedFheInt4 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt4::new);
  }
  public static CompressedFheInt4 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt4::new);
  }
}
