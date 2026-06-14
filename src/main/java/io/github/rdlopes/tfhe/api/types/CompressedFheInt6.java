package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

public final class CompressedFheInt6 extends AbstractCompressedFheType<Byte, FheInt6, CompressedFheInt6> {

  static final Handles<Byte> H = new Handles<>(
      FheValueKind.BYTE,
      TfheHeader::compressed_fhe_int6_decompress,
      TfheHeader::compressed_fhe_int6_clone,
      TfheHeader::compressed_fhe_int6_safe_serialize,
      TfheHeader::compressed_fhe_int6_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_int6_try_encrypt_with_client_key_i8,
      null);

  CompressedFheInt6() { super(TfheHeader::compressed_fhe_int6_destroy); }

  @Override protected Handles<Byte>       handles()         { return H; }
  @Override protected FheInt6             newDecompressed() { return new FheInt6(); }
  @Override protected CompressedFheInt6   newInstance()     { return new CompressedFheInt6(); }

  public static CompressedFheInt6 encrypt(Byte clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt6::new);
  }
  public static CompressedFheInt6 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt6::new);
  }
}
