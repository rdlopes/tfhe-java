package io.github.rdlopes.tfhe.api;

@SuppressWarnings("java:S2975")
public interface FheCloneable<T> extends Cloneable {
  T clone();
}
