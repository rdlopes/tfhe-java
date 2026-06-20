package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.keys.ClientKey;
import java.util.Optional;

public interface FheDecryption<V> {
  V decrypt(ClientKey clientKey);

  Optional<V> tryDecryptTrivial();
}
