package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

public final class CompressedFheUint10 extends AbstractCompressedFheType<Short, FheUint10, CompressedFheUint10> {

  static final Handles<Short> H = new Handles<>(
      FheValueKind.SHORT,
      TfheHeader::compressed_fhe_uint10_decompress,
      TfheHeader::compressed_fhe_uint10_clone,
      TfheHeader::compressed_fhe_uint10_safe_serialize,
      TfheHeader::compressed_fhe_uint10_safe_deserialize_conformant,
      TfheHeader::compressed_fhe_uint10_try_encrypt_with_client_key_u16,
      null);

  CompressedFheUint10() { super(TfheHeader::compressed_fhe_uint10_destroy); }

  @Override protected Handles<Short>       handles()         { return H; }
  @Override protected FheUint10            newDecompressed() { return new FheUint10(); }
  @Override protected CompressedFheUint10  newInstance()     { return new CompressedFheUint10(); }

  public static CompressedFheUint10 encrypt(Short clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint10::new);
  }
  public static CompressedFheUint10 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint10::new);
  }
}
