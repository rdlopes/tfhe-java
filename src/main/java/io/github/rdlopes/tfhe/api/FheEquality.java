package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.types.FheBool;

public interface FheEquality<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>> {
  FheBool equalTo(T other);

  FheBool equalToScalar(V other);

  FheBool notEqualTo(T other);

  FheBool notEqualToScalar(V other);
}
