package io.github.rdlopes.tfhe.api;

public interface CompressedFheType<V, D extends FheType<V, D, T>, T extends CompressedFheType<V, D, T>>
  extends CompressedFheObject<D>, FheCloneable<T> {

}
