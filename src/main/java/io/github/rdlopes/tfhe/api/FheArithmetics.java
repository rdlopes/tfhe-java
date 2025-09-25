package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.types.FheBool;

public interface FheArithmetics<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>> {
  T add(T other);

  CheckedResult<V, T, C> addWithOverflow(T other);

  T addScalar(V other);

  void addAssign(T other);

  void addScalarAssign(V other);

  T subtract(T other);

  CheckedResult<V, T, C> subtractWithOverflow(T other);

  T subtractScalar(V other);

  void subtractAssign(T other);

  void subtractScalarAssign(V other);

  T multiply(T other);

  CheckedResult<V, T, C> multiplyWithOverflow(T other);

  T multiplyScalar(V other);

  void multiplyAssign(T other);

  void multiplyScalarAssign(V other);

  DividerAndRemainder<V, T, C> divideWithRemainder(T other);

  DividerAndRemainder<V, T, C> divideWithRemainderScalar(V other);

  T divide(T other);

  T divideScalar(V other);

  void divideAssign(T other);

  void divideScalarAssign(V other);

  T remainder(T other);

  T remainderScalar(V other);

  void remainderAssign(T other);

  void remainderScalarAssign(V other);

  T negate();

  T ilog2();

  CheckedResult<V, T, C> ilog2WithCheck();

  record CheckedResult<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>>(T result, FheBool check) {
  }

  record DividerAndRemainder<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>>(T divider, T remainder) {
  }
}
