package io.github.rdlopes.tfhe.api;

public interface FheCloneable<T> extends Cloneable {
  T clone();
}
