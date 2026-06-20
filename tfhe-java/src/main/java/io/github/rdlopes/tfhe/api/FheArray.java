package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.types.FheBool;

public interface FheArray<T, A extends FheArray<T, A>> {

  FheBool containsArray(A other);

  FheBool equalsArray(A other);

  T sum();

  A add(A other);

  A subtract(A other);
}
