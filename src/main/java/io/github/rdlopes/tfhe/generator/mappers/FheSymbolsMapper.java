package io.github.rdlopes.tfhe.generator.mappers;

import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappers.impl.*;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

public record FheSymbolsMapper(Path sourcesPath,
                               Path testSourcesPath,
                               String baseSourcePackage,
                               String baseTestPackage)
  implements FheTypeMapperContext {
  private static final Logger logger = LoggerFactory.getLogger(FheSymbolsMapper.class);

  public Collection<ClassMapping> generateMappings(SymbolsIndex symbolsIndex) {
    logger.trace("generateMappings - symbolsIndex: {}", symbolsIndex);

    // Generate FHE type mappings
    Collection<ClassMapping> mappings = new ArrayList<>(
      symbolsIndex.fheTypes()
                  .map(this::createTypeMapper)
                  .flatMap(fheTypeMapper -> fheTypeMapper.generateMappings(symbolsIndex)
                                                         .stream())
                  .toList());

    // Generate FHE key mappings
    mappings.addAll(symbolsIndex.fheKeys()
                                .map(this::createKeyMapper)
                                .flatMap(keyMapper -> keyMapper.generateMappings(symbolsIndex)
                                                               .stream())
                                .toList());

    return mappings;
  }

  private FheKeyMapper createKeyMapper(String keyName) {
    logger.trace("createKeyMapper - keyName: {}", keyName);
    return new FheKeyMapper(this, keyName);
  }

  private @NonNull FheTypeMapper createTypeMapper(String fheTypeName) {
    logger.trace("createTypeMapper - fheTypeName: {}", fheTypeName);
    return switch (fheTypeName) {
      case "FheBool" -> new FheBoolMapper(this, "FheBool");
      case String typeName when (typeName.startsWith("FheInt")) -> new FheIntMapper(this, typeName);
      case String typeName when (typeName.startsWith("FheUint")) -> new FheUintMapper(this, typeName);
      default -> new NoOpMapper(this, fheTypeName);
    };
  }
}
