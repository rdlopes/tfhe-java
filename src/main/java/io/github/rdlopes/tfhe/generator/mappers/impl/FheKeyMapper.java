package io.github.rdlopes.tfhe.generator.mappers.impl;

import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappers.ClassMapping;
import io.github.rdlopes.tfhe.generator.mappers.FheTypeMapperContext;
import io.github.rdlopes.tfhe.generator.mappers.TestValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FheKeyMapper extends AbstractFheTypeMapper {
  private static final Logger logger = LoggerFactory.getLogger(FheKeyMapper.class);

  public FheKeyMapper(FheTypeMapperContext context, String symbolName) {
    logger.trace("init - context: {}, symbolName: {}", context, symbolName);
    super(context, symbolName);
  }

  @Override
  protected Collection<ClassMapping> generateSourceMappings(SymbolsIndex symbolsIndex) {
    logger.trace("generateSourceMappings - symbolsIndex: {}, symbolName: {}", symbolsIndex, getSymbolName());
    String className = getClassNameForSymbol(getSymbolName());
    String keyType = getSymbolName();
    String templateName = getTemplateNameForSymbol(getSymbolName());

    if (className != null && templateName != null) {
      if (isCompressedKey(keyType)) {
        String decompressedKeyType = getDecompressedKeyType(keyType);
        return List.of(sourceClass(templateName, className, dataModel(className,
          Map.entry("keyType", keyType),
          Map.entry("decompressedKeyType", decompressedKeyType))));
      } else {
        return List.of(sourceClass(templateName, className, dataModel(className, Map.entry("keyType", keyType))));
      }
    }
    return List.of();
  }

  private String getClassNameForSymbol(String symbolName) {
    return switch (symbolName) {
      case "client_key" -> "ClientKey";
      case "server_key" -> "ServerKey";
      case "public_key" -> "PublicKey";
      case "compact_public_key" -> "CompactPublicKey";
      case "compressed_server_key" -> "CompressedServerKey";
      case "compressed_compact_public_key" -> "CompressedCompactPublicKey";
      default -> null;
    };
  }

  private String getTemplateNameForSymbol(String symbolName) {
    return switch (symbolName) {
      case "client_key" -> "ClientKey";
      case "server_key" -> "ServerKey";
      case "public_key" -> "PublicKey";
      case "compact_public_key" -> "CompactPublicKey";
      case "compressed_server_key", "compressed_compact_public_key" -> "CompressedKey";
      default -> null;
    };
  }

  private boolean isCompressedKey(String keyType) {
    return keyType.startsWith("compressed_");
  }

  private String getDecompressedKeyType(String keyType) {
    return switch (keyType) {
      case "compressed_server_key" -> "ServerKey";
      case "compressed_compact_public_key" -> "CompactPublicKey";
      default -> null;
    };
  }

  private String getTestTemplateNameForSymbol(String symbolName) {
    return switch (symbolName) {
      case "client_key" -> "ClientKeyTest";
      case "server_key" -> "ServerKeyTest";
      case "public_key" -> "PublicKeyTest";
      case "compact_public_key" -> "CompactPublicKeyTest";
      case "compressed_server_key", "compressed_compact_public_key" -> "CompressedKeyTest";
      default -> null;
    };
  }

  @Override
  protected Collection<ClassMapping> generateTestMappings(SymbolsIndex symbolsIndex) {
    logger.trace("generateTestMappings - symbolsIndex: {}, symbolName: {}", symbolsIndex, getSymbolName());
    String className = getClassNameForSymbol(getSymbolName());
    String keyType = getSymbolName();
    String testTemplateName = getTestTemplateNameForSymbol(getSymbolName());

    if (className != null && testTemplateName != null) {
      String testClassName = className + "Test";
      return List.of(testClass(testTemplateName, testClassName, dataModel(className, Map.entry("keyType", keyType))));
    }
    return List.of();
  }

  @Override
  protected TestValues<?> getTestValues() {
    return new TestValues<>(null, null);
  }

  @Override
  protected Class<?> getDataClass() {
    return Void.class;
  }

  @Override
  protected String getPackage() {
    return "keys";
  }

}
