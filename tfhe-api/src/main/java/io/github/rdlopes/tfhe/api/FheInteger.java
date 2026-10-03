package io.github.rdlopes.tfhe.api;

import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;

/// Base interface for all FHE integer types, both signed and unsigned.
///
/// Arithmetic, comparison, bitwise, and decryption capabilities are declared here.
/// [FheSignedInteger#abs()] is intentionally absent — it lives on [FheSignedInteger] only,
/// to avoid LSP violations on unsigned implementations.
///
/// Random generation is available as static factory methods on each concrete type.
///
/// @param <V> the Java clear-text type
/// @param <T> the concrete encrypted type
/// @param <C> the corresponding compressed type
public interface FheInteger<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>>
  extends FheType<V, T, C>, FheArithmetics<V, T>, FheComparison<V, T>, FheBitwise<V, T>, FheDecryption<V> {

  /// Evaluates an arbitrary univariate lookup table over the full domain of this integer type.
  /// Supported directly for types with bitwidth <= 8. For larger bitwidths, use
  /// [applyLookupTable(LongUnaryOperator, long, long)] to specify domain bounds.
  T applyLookupTable(LongUnaryOperator mapping);

  /// Evaluates an arbitrary univariate lookup table over the specified [minDomain, maxDomain] interval.
  T applyLookupTable(LongUnaryOperator mapping, long minDomain, long maxDomain);

  /// Evaluates an arbitrary bivariate lookup table with [other] over their full domains.
  /// Supported directly for types with bitwidth <= 4. For larger bitwidths, use
  /// the bounded overload specifying domain intervals.
  T applyBivariateLookupTable(T other, LongBinaryOperator mapping);

  /// Evaluates an arbitrary bivariate lookup table with [other] over the specified domain intervals.
  T applyBivariateLookupTable(T other, LongBinaryOperator mapping,
                              long minDomainX, long maxDomainX,
                              long minDomainY, long maxDomainY);
}
