package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.keys.ClientKey;

public interface FheDecryption<V> {
  V decrypt(ClientKey clientKey);
}
