package io.github.rdlopes.tfhe.generator.mappers;

import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappers.impl.FheBoolMapper;
import io.github.rdlopes.tfhe.generator.mappers.impl.FheIntMapper;
import io.github.rdlopes.tfhe.generator.mappers.impl.FheUintMapper;
import io.github.rdlopes.tfhe.generator.mappers.impl.NoOpMapper;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Collection;

public record FheTypesMapper(Path sourcesPath,
                             Path testSourcesPath,
                             String baseSourcePackage,
                             String baseTestPackage)
  implements FheTypeMapper, FheTypeMapperContext {
  private static final Logger logger = LoggerFactory.getLogger(FheTypesMapper.class);

  @Override
  public Collection<ClassMapping> generateMappings(SymbolsIndex symbolsIndex) {
    logger.trace("generateMappings - symbolsIndex: {}", symbolsIndex);
    return symbolsIndex.fheTypes()
                       .map(this::createMapper)
                       .flatMap(fheTypeMapper -> fheTypeMapper.generateMappings(symbolsIndex)
                                                              .stream())
                       .toList();
  }

  private @NonNull FheTypeMapper createMapper(String fheTypeName) {
    logger.trace("createMapper - fheTypeName: {}", fheTypeName);
    return switch (fheTypeName) {
      case "FheBool" -> new FheBoolMapper(this, "FheBool");
      case String typeName when (typeName.startsWith("FheInt")) -> new FheIntMapper(this, typeName);
      case String typeName when (typeName.startsWith("FheUint")) -> new FheUintMapper(this, typeName);
      default -> new NoOpMapper(this, fheTypeName);
    };
  }
}
