package io.github.rdlopes.tfhe.api;

public interface FheInteger<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>>
  extends FheType<V, T, C>, FheArithmetics<V, T, C>, FheComparison<V, T, C>, FheBitwise<V, T, C>, FheDecryption<V, T, C>, FheRandom<V, T, C> {

  T abs();

}
