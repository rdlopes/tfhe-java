package io.github.rdlopes.tfhe.api;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

public interface FheValueHolder {

  @NonNull
  BigInteger getValue();

  void setValue(@NonNull BigInteger value);
}
