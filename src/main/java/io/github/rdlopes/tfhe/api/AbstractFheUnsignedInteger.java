package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.ffm.FheTypeHandles;

import java.lang.foreign.MemorySegment;
import java.util.function.Function;

/// Abstract base for all unsigned FHE integer types.
///
/// The only behavioural difference from signed types is that `abs()` is
/// meaningless for unsigned values — they are always non-negative by definition.
/// This class overrides [AbstractFheType#abs()] to throw
/// [UnsupportedOperationException], preserving Liskov correctness given
/// that the [FheUnsignedInteger] interface does not declare `abs()`.
public abstract class AbstractFheUnsignedInteger<
    V,
    T extends AbstractFheUnsignedInteger<V, T, C>,
    C extends AbstractCompressedFheType<V, T, C>>
    extends AbstractFheType<V, T, C>
    implements FheUnsignedInteger<V, T, C> {

  protected AbstractFheUnsignedInteger(Function<MemorySegment, Integer> destroyRef) {
    super(destroyRef);
  }

  /// `abs()` is not defined for unsigned integers — they are always ≥ 0.
///
/// @throws UnsupportedOperationException always
  @Override
  public final T abs() {
    throw new UnsupportedOperationException(
        "abs() is not defined for unsigned type " + getClass().getSimpleName());
  }
}
