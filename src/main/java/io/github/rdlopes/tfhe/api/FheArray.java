package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.types.FheBool;

public interface FheArray<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>, A extends FheArray<V, T, C, A>> {

  FheBool containsArray(A other);

  FheBool equalsArray(A other);

  T sum();
}
