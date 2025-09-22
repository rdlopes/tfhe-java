package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.serde.FheSerialization;

public interface CompressedFheObject<T extends FheObject> extends FheSerialization {
  T decompress();
}
