package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.U256;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/// Compressed encrypted unsigned 160-bit integer.
public final class CompressedFheUint160 extends AbstractCompressedFheType<U256, FheUint160, CompressedFheUint160> {

  static final Handles<U256> H = new Handles<>(
    new FheValueKind.Wide<>(U256::newEmpty),
      TfheHeader::compressed_fhe_uint160_decompress,
      TfheHeader::compressed_fhe_uint160_clone,
      TfheHeader::compressed_fhe_uint160_safe_serialize,
      TfheHeader::compressed_fhe_uint160_safe_deserialize_conformant,
      null,
      TfheHeader::compressed_fhe_uint160_try_encrypt_with_client_key_u256);

  CompressedFheUint160() { super(TfheHeader::compressed_fhe_uint160_destroy); }

  @Override protected Handles<U256>         handles()         { return H; }
  @Override protected FheUint160            newDecompressed() { return new FheUint160(); }
  @Override protected CompressedFheUint160  newInstance()     { return new CompressedFheUint160(); }

  public static CompressedFheUint160 encrypt(U256 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheUint160::new);
  }
  public static CompressedFheUint160 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheUint160::new);
  }
}
