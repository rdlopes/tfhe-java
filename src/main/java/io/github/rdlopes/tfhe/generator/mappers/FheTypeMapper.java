package io.github.rdlopes.tfhe.generator.mappers;

import io.github.rdlopes.tfhe.generator.SymbolsIndex;

import java.util.Collection;

public interface FheTypeMapper {
  Collection<ClassMapping> generateMappings(SymbolsIndex symbolsIndex);
}
