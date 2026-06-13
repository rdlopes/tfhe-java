package io.github.rdlopes.tfhe.api;

public interface FheUnsignedInteger<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>>
  extends FheType<V, T, C>, FheArithmetics<V, T>, FheComparison<V, T>, FheBitwise<V, T>, FheDecryption<V>, FheRandom<T> {
}
