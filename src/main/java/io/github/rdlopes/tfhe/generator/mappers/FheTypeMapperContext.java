package io.github.rdlopes.tfhe.generator.mappers;

import java.nio.file.Path;
import java.util.Optional;

public interface FheTypeMapperContext {
  Path sourcesPath();

  Path testSourcesPath();

  String baseSourcePackage();

  String baseTestPackage();

  default String resolveSourcePackage(String packageName) {
    return baseSourcePackage() + Optional.ofNullable(packageName)
                                         .filter(p -> !p.isBlank())
                                         .map(p -> "." + p)
                                         .orElse("");
  }

  default String resolveTestPackage(String packageName) {
    return baseTestPackage() + Optional.ofNullable(packageName)
                                       .filter(p -> !p.isBlank())
                                       .map(p -> "." + p)
                                       .orElse("");
  }
}
