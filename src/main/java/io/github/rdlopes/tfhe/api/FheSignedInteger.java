package io.github.rdlopes.tfhe.api;

/// Capability interface for signed FHE integer types.
///
/// Signed types support [#abs()], which is mathematically undefined for
/// unsigned values. By placing `abs()` only here — and having
/// [FheUnsignedInteger] *not* extend this interface — the hierarchy
/// satisfies the Liskov Substitution Principle: no caller holding a
/// [FheInteger] reference can accidentally invoke `abs()` on an
/// unsigned value.
///
/// @param <V> the Java clear-text type (e.g. `Byte`, `I128`)
/// @param <T> the concrete encrypted type (e.g. `FheInt8`)
/// @param <C> the corresponding compressed type (e.g. `CompressedFheInt8`)
public interface FheSignedInteger<
    V,
    T extends FheType<V, T, C>,
    C extends CompressedFheType<V, T, C>>
    extends FheInteger<V, T, C> {

  /// Returns the absolute value of this encrypted signed integer.
  ///
  /// @return a new encrypted integer holding `|this|`
  T abs();
}
