package io.github.rdlopes.tfhe.generator.mappings;

import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.templates.SourceFileMapping;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;

import static java.io.File.separatorChar;

public record FheMapper(
  Path sourcesPath,
  Path testSourcesPath,
  String baseSourcePackage,
  String baseTestPackage) {
  private static final Logger logger = LoggerFactory.getLogger(FheMapper.class);

  public Collection<SourceFileMapping> mapTypes(SymbolsIndex symbolsIndex) {
    return symbolsIndex.lookupFiltered(name -> name.startsWith("Type_"))
                       .map(name -> StringUtils.substringAfter(name, "_"))
                       .flatMap(typeName ->
                         new FheTypeMapper(this)
                           .generateMappings(typeName)
                           .stream())
                       .filter(Objects::nonNull)
                       .toList();
  }

  String sourcePackage(String packageExtension) {
    return baseSourcePackage() + "." + packageExtension;
  }

  String testPackage(String packageExtension) {
    return baseTestPackage() + "." + packageExtension;
  }

  Path sourcePath(String packageName, String className) {
    return sourcesPath().resolve(packageName.replace('.', separatorChar))
                        .resolve(className + ".java");
  }

  Path testPath(String packageName, String className) {
    return testSourcesPath().resolve(packageName.replace('.', separatorChar))
                            .resolve(className + ".java");
  }

}
