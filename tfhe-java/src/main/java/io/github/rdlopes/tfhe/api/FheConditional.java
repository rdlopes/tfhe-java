package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.types.FheBool;

public interface FheConditional<T> {
  T ifThenElse(FheBool condition, T elseValue);
}
