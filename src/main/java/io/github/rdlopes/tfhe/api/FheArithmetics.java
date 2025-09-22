package io.github.rdlopes.tfhe.api;

import io.github.rdlopes.tfhe.api.types.FheBool;

import java.util.Map;

public interface FheArithmetics<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>> {
  T add(T other);

  Map.Entry<T, FheBool> addWithOverflow(T other);

  T addScalar(V other);

  void addAssign(T other);

  void addScalarAssign(V other);

  T subtract(T other);

  Map.Entry<T, FheBool> subtractWithOverflow(T other);

  T subtractScalar(V other);

  void subtractAssign(T other);

  void subtractScalarAssign(V other);

  T multiply(T other);

  Map.Entry<T, FheBool> multiplyWithOverflow(T other);

  T multiplyScalar(V other);

  void multiplyAssign(T other);

  void multiplyScalarAssign(V other);

  Map.Entry<T, T> divideWithRemainder(T other);

  Map.Entry<T, T> divideWithRemainderScalar(V other);

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

  Map.Entry<T, FheBool> ilog2WithCheck();
}
