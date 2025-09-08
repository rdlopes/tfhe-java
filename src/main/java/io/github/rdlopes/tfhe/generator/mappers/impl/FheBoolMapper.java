package io.github.rdlopes.tfhe.generator.mappers.impl;

import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappers.ClassMapping;
import io.github.rdlopes.tfhe.generator.mappers.FheTypeMapperContext;
import io.github.rdlopes.tfhe.generator.mappers.TestValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

public class FheBoolMapper extends AbstractFheTypeMapper {
  private static final Logger logger = LoggerFactory.getLogger(FheBoolMapper.class);

  public FheBoolMapper(FheTypeMapperContext context, String typeName) {
    logger.trace("init - context: {}, typeName: {}", context, typeName);
    super(context, typeName);
  }

  @Override
  protected Collection<ClassMapping> generateSourceMappings(SymbolsIndex symbolsIndex) {
    logger.trace("generateSourceMappings - symbolsIndex: {}", symbolsIndex);
    return List.of(
      sourceClass("FheBool", "FheBool"),
      sourceClass("Compressed", "CompressedFheBool")
    );
  }

  @Override
  protected Collection<ClassMapping> generateTestMappings(SymbolsIndex symbolsIndex) {
    logger.trace("generateTestMappings - symbolsIndex: {}", symbolsIndex);
    return List.of(
      testClass("FheBoolTest", "FheBoolTest"),
      testClass("CompressedTest", "CompressedFheBoolTest")
    );
  }

  @Override
  protected TestValues<Boolean> getTestValues() {
    return new TestValues<>(true, false);
  }

  @Override
  protected Class<?> getDataClass() {
    return Boolean.class;
  }

  @Override
  protected String getPackage() {
    return "booleans";
  }

}
