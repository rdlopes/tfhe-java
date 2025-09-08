package io.github.rdlopes.tfhe.generator.mappers.impl;

import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappers.ClassMapping;
import io.github.rdlopes.tfhe.generator.mappers.FheTypeMapper;
import io.github.rdlopes.tfhe.generator.mappers.FheTypeMapperContext;
import io.github.rdlopes.tfhe.generator.mappers.TestValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFheTypeMapper implements FheTypeMapper {
  private static final Logger logger = LoggerFactory.getLogger(AbstractFheTypeMapper.class);
  private final FheTypeMapperContext context;
  private final String typeName;

  protected AbstractFheTypeMapper(FheTypeMapperContext context, String typeName) {
    logger.trace("init - context: {}, typeName: {}", context, typeName);
    this.context = context;
    this.typeName = typeName;
  }

  public FheTypeMapperContext getContext() {
    return context;
  }

  public String getTypeName() {
    return typeName;
  }

  @SafeVarargs
  protected final Map<String, Object> dataModel(String className, Map.Entry<String, Object>... entries) {
    Map<String, Object> baseModel = new HashMap<>(Map.of(
      "sourcePackageName", getContext().resolveSourcePackage(getPackage()),
      "testPackageName", getContext().resolveTestPackage(getPackage()),
      "className", className,
      "compressedClassName", "Compressed" + className,
      "testClassName", className + "Test",
      "testCompressedClassName", "Compressed" + className + "Test",
      "dataClass", getDataClass(),
      "testValues", getTestValues()
    ));
    baseModel.putAll(Map.ofEntries(entries));
    return baseModel;
  }

  @SafeVarargs
  protected final ClassMapping sourceClass(String templateName, String generatedClassName, Map.Entry<String, Object>... entries) {
    return new ClassMapping(
      templateName + ".ftl",
      getContext().sourcesPath(),
      getContext().resolveSourcePackage(getPackage()),
      generatedClassName + ".java",
      dataModel(getTypeName(), entries)
    );
  }

  @SafeVarargs
  protected final ClassMapping testClass(String templateName, String generatedClassName, Map.Entry<String, Object>... entries) {
    return new ClassMapping(
      templateName + ".ftl",
      getContext().testSourcesPath(),
      getContext().resolveTestPackage(getPackage()),
      generatedClassName + ".java",
      dataModel(getTypeName(), entries)
    );
  }

  @Override
  public final Collection<ClassMapping> generateMappings(SymbolsIndex symbolsIndex) {
    logger.trace("generateMappings - symbolsIndex: {}", symbolsIndex);

    Collection<ClassMapping> classMappings = new ArrayList<>();
    classMappings.addAll(generateSourceMappings(symbolsIndex));
    classMappings.addAll(generateTestMappings(symbolsIndex));

    return classMappings;
  }

  protected abstract Collection<ClassMapping> generateSourceMappings(SymbolsIndex symbolsIndex);

  protected abstract Collection<ClassMapping> generateTestMappings(SymbolsIndex symbolsIndex);

  protected abstract TestValues<?> getTestValues();

  protected abstract Class<?> getDataClass();

  protected abstract String getPackage();

}
