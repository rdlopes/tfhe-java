package io.github.rdlopes.tfhe.api;

public interface FheRandom<T> {

  T random(long seedLow, long seedHigh, long bitsCount);

  T random(long seedLow, long seedHigh);
}
