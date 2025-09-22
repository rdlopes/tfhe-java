package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.types.FheBool;

public interface FheComparison<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>> {

  FheBool lessThan(T other);

  FheBool lessThanScalar(V other);

  FheBool lessThanOrEqualTo(T other);

  FheBool lessThanOrEqualToScalar(V other);

  FheBool greaterThan(T other);

  FheBool greaterThanScalar(V other);

  FheBool greaterThanOrEqualTo(T other);

  FheBool greaterThanOrEqualToScalar(V other);

  T min(T other);

  T minScalar(V other);

  T max(T other);

  T maxScalar(V other);
}
