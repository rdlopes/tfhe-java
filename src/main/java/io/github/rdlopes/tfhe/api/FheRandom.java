package io.github.rdlopes.tfhe.api;

public interface FheRandom<V, T extends FheType<V, T, C>, C extends CompressedFheType<V, T, C>> {

  T random(long seedLow, long seedHigh, long bitsCount);

  T random(long seedLow, long seedHigh);
}
