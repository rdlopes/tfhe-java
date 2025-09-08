package io.github.rdlopes.tfhe.generator.mappers.impl;

import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappers.ClassMapping;
import io.github.rdlopes.tfhe.generator.mappers.FheTypeMapperContext;
import io.github.rdlopes.tfhe.generator.mappers.TestValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class NoOpMapper extends AbstractFheTypeMapper {
  private static final Logger logger = LoggerFactory.getLogger(NoOpMapper.class);

  public NoOpMapper(FheTypeMapperContext context, String typeName) {
    logger.trace("init - context: {}, typeName: {}", context, typeName);
    super(context, typeName);
  }

  @Override
  protected Collection<ClassMapping> generateSourceMappings(SymbolsIndex symbolsIndex) {
    logger.trace("generateSourceMappings - symbolsIndex: {}", symbolsIndex);
    return List.of();
  }

  @Override
  protected Collection<ClassMapping> generateTestMappings(SymbolsIndex symbolsIndex) {
    logger.trace("generateTestMappings - symbolsIndex: {}", symbolsIndex);
    return List.of();
  }

  @Override
  protected TestValues<?> getTestValues() {
    return null;
  }

  @Override
  protected Class<?> getDataClass() {
    return null;
  }

  @Override
  protected String getPackage() {
    return null;
  }

}
