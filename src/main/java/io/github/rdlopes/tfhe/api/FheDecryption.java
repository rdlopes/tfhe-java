package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.keys.ClientKey;

public interface FheDecryption<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>> {
  V decrypt(ClientKey clientKey);
}
