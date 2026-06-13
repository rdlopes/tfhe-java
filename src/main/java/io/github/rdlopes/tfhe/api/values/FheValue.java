package io.github.rdlopes.tfhe.api.values;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

/**
 * Represents a value encrypted or managed by the TFHE scheme.
 */
public interface FheValue {

  /**
   * Gets the cleartext value represented by this FheValue.
   *
   * @return the cleartext value as a {@link BigInteger}
   */
  @NonNull
  BigInteger getValue();

  /**
   * Sets the cleartext value for this FheValue.
   *
   * @param value the cleartext value as a {@link BigInteger} to set
   */
  void setValue(@NonNull BigInteger value);
}
