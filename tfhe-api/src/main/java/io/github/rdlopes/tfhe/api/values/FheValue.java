package io.github.rdlopes.tfhe.api.values;

import org.jspecify.annotations.NonNull;

import java.math.BigInteger;

/// Immutable read-only view of a wide cleartext value (128–2048 bit).
///
/// Implementations hold a snapshot of a native FFM memory region at creation
/// time. The value is written during construction and is never mutated afterward,
/// making `FheValue` instances safe to share across threads without
/// synchronization.
///
/// Use the static factory methods on each concrete subtype to obtain instances:
/// ```java
///   U128 value = U128.of(BigInteger.TWO);
///   BigInteger back = value.asBigInteger();
/// ```
public interface FheValue {
  
  /// Returns the cleartext value represented by this instance as a [BigInteger].
  ///
  /// @return the cleartext value; never `null`
  @NonNull
  BigInteger asBigInteger();

}
