package io.github.rdlopes.tfhe.api;

public interface FheLogic<V, T> {
  T bitAnd(T other);

  T bitAndScalar(V other);

  void bitAndAssign(T other);

  void bitAndScalarAssign(V other);

  T bitOr(T other);

  T bitOrScalar(V other);

  void bitOrAssign(T other);

  void bitOrScalarAssign(V other);

  T bitXor(T other);

  T bitXorScalar(V other);

  void bitXorAssign(T other);

  void bitXorScalarAssign(V other);

  T bitNot();
}
