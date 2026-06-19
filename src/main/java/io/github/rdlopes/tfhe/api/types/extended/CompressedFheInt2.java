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
public final class CompressedFheInt2 extends AbstractCompressedFheType<Byte, FheInt2, CompressedFheInt2> {

  static final Handles<Byte> H = new Handles<>(
      FheValueKind.BYTE,
      TfheHeader::compressed_fhe_int2_decompress,
      TfheHeader::compressed_fhe_int2_clone,
      TfheHeader::compressed_fhe_int2_safe_serialize,
      TfheHeader::compressed_fhe_int2_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_int2_try_encrypt_with_client_key_i8,
      null);

  CompressedFheInt2() { super(TfheHeader::compressed_fhe_int2_destroy); }

  @Override protected Handles<Byte>       handles()         { return H; }
  @Override protected FheInt2             newDecompressed() { return new FheInt2(); }
  @Override protected CompressedFheInt2   newInstance()     { return new CompressedFheInt2(); }

  public static CompressedFheInt2 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt2::new);
  }
  public static CompressedFheInt2 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt2::new);
  }
}
