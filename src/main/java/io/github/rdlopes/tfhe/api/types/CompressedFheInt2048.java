package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.AbstractCompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.api.values.I2048;
import io.github.rdlopes.tfhe.ffm.FheValueKind;
import io.github.rdlopes.tfhe.ffm.TfheHeader;

/**
 * Compressed encrypted signed 2048-bit integer.
 */
public final class CompressedFheInt2048 extends AbstractCompressedFheType<I2048, FheInt2048, CompressedFheInt2048> {

  static final Handles<I2048> H = new Handles<>(
      new FheValueKind.Wide<>(I2048::new),
      TfheHeader::compressed_fhe_int2048_decompress,
      TfheHeader::compressed_fhe_int2048_clone,
      TfheHeader::compressed_fhe_int2048_safe_serialize,
      TfheHeader::compressed_fhe_int2048_safe_deserialize_conformant,
      null,                                                            // encryptClientKey — N/A for Wide
      TfheHeader::compressed_fhe_int2048_try_encrypt_with_client_key_i2048);

  CompressedFheInt2048() { super(TfheHeader::compressed_fhe_int2048_destroy); }

  @Override protected Handles<I2048>        handles()         { return H; }
  @Override protected FheInt2048            newDecompressed() { return new FheInt2048(); }
  @Override protected CompressedFheInt2048  newInstance()     { return new CompressedFheInt2048(); }

  public static CompressedFheInt2048 encrypt(I2048 clearValue, ClientKey clientKey) {
    return encryptClientKey(H, clearValue, clientKey, CompressedFheInt2048::new);
  }
  public static CompressedFheInt2048 deserialize(DynamicBuffer buffer, ServerKey serverKey) {
    return deserialize(H, buffer, serverKey, CompressedFheInt2048::new);
  }
}
