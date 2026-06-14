package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.types.FheBool;
import io.github.rdlopes.tfhe.ffm.FheOps.*;
import io.github.rdlopes.tfhe.ffm.NativeArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;

/// Abstract base for unsigned FHE array types (e.g. `FheUint8Array`).
///
/// Unsigned arrays have native `containsArray`, `equalsArray`,
/// and `sum` operations. Signed arrays do <em>not</em> extend this class
/// — they delegate via cast to their unsigned counterpart.
///
/// `add` and `subtract` are element-wise operations that delegate
/// to the element type and are implemented here for all array types.
///
/// @param <E> the element type (e.g. `FheUint8`)
/// @param <A> the array type itself
public abstract class AbstractFheArray<
    E extends AbstractFheType<?, E, ?>,
    A extends AbstractFheArray<E, A>>
    extends NativeArray
    implements FheArray<E, A> {

  protected AbstractFheArray(Collection<E> elements) {
    super(elements);
  }

  // ── Subclass metadata ────────────────────────────────────────────────────

  /// Native operation for array sub-slice containment.
  protected abstract ArrayBinaryOp containsArrayOp();

  /// Native operation for array equality.
  protected abstract ArrayBinaryOp equalsArrayOp();

  /// Native operation for array sum.
  protected abstract ArraySumOp sumOp();

  /// Allocates a new empty element (output slot).
  protected abstract E newElement();

  /// Creates a new array from a list of elements.
  protected abstract A newArray(List<E> elements);

  // ══════════════════════════════════════════════════════════════════════════
  // FheArray interface — all final
  // ══════════════════════════════════════════════════════════════════════════

  @Override
  public final FheBool containsArray(A other) {
    FheBool r = FheBool.newEmpty();
    execute(() -> containsArrayOp().apply(
        getAddress(), getSize(), other.getAddress(), other.getSize(), r.getAddress()));
    return r;
  }

  @Override
  public final FheBool equalsArray(A other) {
    FheBool r = FheBool.newEmpty();
    execute(() -> equalsArrayOp().apply(
        getAddress(), getSize(), other.getAddress(), other.getSize(), r.getAddress()));
    return r;
  }

  @Override
  public final E sum() {
    E r = newElement();
    execute(() -> sumOp().apply(getAddress(), getSize(), r.getAddress()));
    return r;
  }

  @Override
  public final A add(A other) {
    sizeCheck(other);
    List<E> t = this.getElements();
    List<E> o = other.getElements();
    List<E> result = new ArrayList<>(t.size());
    for (int i = 0; i < t.size(); i++) {
      result.add(t.get(i).add(o.get(i)));
    }
    return newArray(result);
  }

  @Override
  public final A subtract(A other) {
    sizeCheck(other);
    List<E> t = this.getElements();
    List<E> o = other.getElements();
    List<E> result = new ArrayList<>(t.size());
    for (int i = 0; i < t.size(); i++) {
      result.add(t.get(i).subtract(o.get(i)));
    }
    return newArray(result);
  }

  // ── Helpers ───────────────────────────────────────────────────────────────

  private void sizeCheck(A other) {
    if (getSize() != other.getSize()) {
      throw new IllegalArgumentException(
          "Array sizes must match: " + getSize() + " vs " + other.getSize());
    }
  }
}
