package io.github.rdlopes.tfhe.api;

public interface FheBoolean<T extends FheType<Boolean, T, C>, C extends CompressedFheType<Boolean, T, C>>
  extends FheType<Boolean, T, C>, FheDecryption<Boolean, T, C> {
}
