package io.github.rdlopes.tfhe.api;

public interface FheBitwise<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>> {

  T shiftLeft(T shift);

  T shiftLeftScalar(V shift);

  void shiftLeftAssign(T shift);

  void shiftLeftScalarAssign(V shift);

  T shiftRight(T shift);

  T shiftRightScalar(V shift);

  void shiftRightAssign(T shift);

  void shiftRightScalarAssign(V shift);

  T rotateLeft(T shift);

  T rotateLeftScalar(V shift);

  void rotateLeftAssign(T shift);

  void rotateLeftScalarAssign(V shift);

  T rotateRight(T shift);

  T rotateRightScalar(V shift);

  void rotateRightAssign(T shift);

  void rotateRightScalarAssign(V shift);

  T leadingOnes();

  T leadingZeros();

  T trailingOnes();

  T trailingZeros();
}
