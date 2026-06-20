package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.types.FheBool;

public interface FheEquality<V, T> {
  FheBool equalTo(T other);

  FheBool equalToScalar(V other);

  FheBool notEqualTo(T other);

  FheBool notEqualToScalar(V other);
}
