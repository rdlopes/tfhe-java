package io.github.rdlopes.tfhe.api.values;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

public interface FheValue {

  @NonNull
  BigInteger getValue();

  void setValue(@NonNull BigInteger value);
}
