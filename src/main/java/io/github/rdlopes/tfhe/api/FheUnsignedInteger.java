package io.github.rdlopes.tfhe.api;

/// Marker interface for unsigned FHE integer types.
///
/// Does not extend [FheSignedInteger] and therefore does not expose `abs()`.
/// Random generation is available as static factory methods on each concrete type.
///
/// @param <V> the Java clear-text type (e.g. `Byte`)
/// @param <T> the concrete encrypted type (e.g. `FheUint8`)
/// @param <C> the corresponding compressed type (e.g. `CompressedFheUint8`)
public interface FheUnsignedInteger<
  V,
  T extends FheType<V, T, C>,
  C extends CompressedFheType<V, T, C>>
  extends FheInteger<V, T, C> {
}
