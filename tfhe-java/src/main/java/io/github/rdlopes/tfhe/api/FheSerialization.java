package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;

public interface FheSerialization {
  DynamicBuffer serialize();
}
