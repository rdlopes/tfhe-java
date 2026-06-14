package io.github.rdlopes.tfhe.api;

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
}
