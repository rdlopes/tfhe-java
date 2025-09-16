package io.github.rdlopes.tfhe.generator.mappings;

import io.github.rdlopes.tfhe.ffm.NativeValue;
import io.github.rdlopes.tfhe.generator.templates.SourceFileMapping;
import io.github.rdlopes.tfhe.internal.data.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

public record FheTypeMapper(FheMapper fheMapper) {
  private static final Logger logger = LoggerFactory.getLogger(FheTypeMapper.class);
  private static final List<String> IGNORED_TYPES = List.of("FheAsciiString");

  static boolean isSigned(String nativeName) {
    return nativeName.startsWith("fhe_int");
  }

  static int bitLength(int bitSize) {
    return switch (bitSize) {
      case int b when (b <= 8) -> 8;
      case int b when (b <= 16) -> 16;
      case int b when (b <= 32) -> 32;
      case int b when (b <= 64) -> 64;
      case int b when (b <= 128) -> 128;
      case int b when (b <= 256) -> 256;
      case int b when (b <= 512) -> 512;
      case int b when (b <= 1024) -> 1024;
      case int b when (b <= 2048) -> 2048;
      default -> throw new IllegalArgumentException("Unsupported bit size: " + bitSize);
    };
  }

  static Class<?> dataClass(String nativeName, int bitSize) {
    return switch (bitSize) {
      case int _ when (nativeName.contains("bool")) -> boolean.class;
      case int _ when (nativeName.contains("string")) -> String.class;
      case int b when (b <= 8) -> byte.class;
      case int b when (b <= 16) -> short.class;
      case int b when (b <= 32) -> int.class;
      case int b when (b <= 64) -> long.class;
      default -> BigInteger.class;
    };
  }

  static Class<?> valueClass(int bitLength, boolean signed) {
    return switch (bitLength) {
      case 128 -> signed ? I128.class : U128.class;
      case 256 -> signed ? I256.class : U256.class;
      case 512 -> signed ? I512.class : U512.class;
      case 1024 -> signed ? I1024.class : U1024.class;
      case 2048 -> signed ? I2048.class : U2048.class;
      default -> null;
    };
  }

  public Collection<SourceFileMapping> generateMappings(String typeName) {
    logger.trace("generateMappings - typeName: {}", typeName);

    if (IGNORED_TYPES.contains(typeName)) {
      return emptyList();
    }

    String[] nameParts = StringUtils.splitByCharacterTypeCamelCase(typeName);
    String nativeName = Arrays.stream(nameParts)
                              .takeWhile(StringUtils::isAlpha)
                              .map(String::toLowerCase)
                              .collect(joining("_")) +
      (StringUtils.isNumeric(nameParts[nameParts.length - 1])
        ? nameParts[nameParts.length - 1]
        : "");

    int bitSize = StringUtils.isNumeric(nameParts[nameParts.length - 1])
      ? Integer.parseInt(nameParts[nameParts.length - 1])
      : 8;

    String sourcePackageName = fheMapper().sourcePackage("types");
    String testPackageName = fheMapper().testPackage("types");
    Path sourcePath = fheMapper().sourcePath(sourcePackageName, typeName);
    Path compressedSourcePath = fheMapper().sourcePath(sourcePackageName, "Compressed" + typeName);
    Path testPath = fheMapper().testPath(testPackageName, typeName + "Test");
    Path compressedTestPath = fheMapper().testPath(testPackageName, "Compressed" + typeName + "Test");

    FheTypeContext fheTypeContext = new FheTypeContext(
      sourcePackageName,
      typeName,
      NativeValue.class,
      nativeName,
      bitSize);

    FheTypeTestContext<FheTypeContext> fheTypeTestTemplateContext = new FheTypeTestContext<>(
      testPackageName,
      typeName + "Test",
      fheTypeContext
    );

    CompressedFheTypeContext<FheTypeContext> compressedFheTypeContext = new CompressedFheTypeContext<>(
      sourcePackageName,
      "Compressed" + typeName,
      NativeValue.class,
      fheTypeContext
    );

    FheTypeTestContext<CompressedFheTypeContext<FheTypeContext>> compressedFheTypeTestContext = new FheTypeTestContext<>(
      testPackageName,
      "Compressed" + typeName + "Test",
      compressedFheTypeContext
    );

    return List.of(
      new SourceFileMapping("SourceClass", sourcePath, fheTypeContext),
      new SourceFileMapping("SourceClass", compressedSourcePath, compressedFheTypeContext),
      new SourceFileMapping("TestClass", testPath, fheTypeTestTemplateContext),
      new SourceFileMapping("TestClass", compressedTestPath, compressedFheTypeTestContext)
    );
  }

}
