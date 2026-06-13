package io.github.rdlopes.tfhe.api;

public interface FheType<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>>
  extends FheObject, FheCloneable<T>, FheLogic<V, T>, FheEquality<V, T>, FheEncryption<V, T> {

  C compress();
}
