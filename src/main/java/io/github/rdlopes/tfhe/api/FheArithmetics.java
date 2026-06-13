package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.types.FheBool;

public interface FheArithmetics<V, T> {
  T add(T other);

  CheckedResult<T> addWithOverflow(T other);

  T addScalar(V other);

  void addAssign(T other);

  void addScalarAssign(V other);

  T subtract(T other);

  CheckedResult<T> subtractWithOverflow(T other);

  T subtractScalar(V other);

  void subtractAssign(T other);

  void subtractScalarAssign(V other);

  T multiply(T other);

  CheckedResult<T> multiplyWithOverflow(T other);

  T multiplyScalar(V other);

  void multiplyAssign(T other);

  void multiplyScalarAssign(V other);

  DividerAndRemainder<T> divideWithRemainder(T other);

  DividerAndRemainder<T> divideWithRemainderScalar(V other);

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

  CheckedResult<T> ilog2WithCheck();

  record CheckedResult<T>(T result, FheBool check) {
  }

  record DividerAndRemainder<T>(T divider, T remainder) {
  }
}
