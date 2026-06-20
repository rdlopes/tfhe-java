package io.github.rdlopes.tfhe.api;

public interface CompressedFheObject<T extends FheObject> extends FheSerialization {
  T decompress();
}
