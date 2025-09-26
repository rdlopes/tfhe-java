package io.github.rdlopes.tfhe.generator.templates;

import io.github.rdlopes.tfhe.api.values.*;
import io.github.rdlopes.tfhe.generator.parsers.SymbolsIndex;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;
import static org.apache.commons.lang3.StringUtils.*;

public record TemplateContext(String packageName, String className, String typeName, SymbolsIndex symbolsIndex) {

  public static TemplateContext forType(String packageName, String fheType, SymbolsIndex symbolsIndex) {
    return new TemplateContext(packageName, fheType, fheType, symbolsIndex.withFilter(s -> s.equals(fheType) || s.startsWith(nativePrefix(fheType))));
  }

  public static String nativePrefix(String type) {
    String nativeType = Arrays.stream(splitByCharacterTypeCamelCase(type))
                              .map(String::toLowerCase)
                              .map(s -> isAlpha(s) ? "_" + s : s)
                              .collect(joining());

    return substringAfter(nativeType, "_") + "_";
  }

  public static TemplateContext forCompressedType(String packageName, String fheType, SymbolsIndex symbolsIndex) {
    String className = "Compressed" + fheType;
    return new TemplateContext(packageName, className, fheType, symbolsIndex.withFilter(s -> s.equals(className) || s.startsWith(nativePrefix(className))));
  }

  public static TemplateContext forArrayType(String packageName, String fheType, SymbolsIndex symbolsIndex) {
    String className = fheType + "Array";
    return new TemplateContext(packageName, className, fheType, symbolsIndex.withFilter(s -> s.equals(fheType) || s.startsWith(nativePrefix(fheType))));
  }

  public boolean isBoolean() {
    return className().startsWith("FheBool");
  }

  public boolean isInteger() {
    return className().startsWith("FheInt") || className().startsWith("FheUint");
  }

  public boolean isSigned() {
    return !className().contains("Uint");
  }

  public boolean hasArray() {
    return symbolsIndex().lookupSymbol(nativePrefix(className())) != null;
  }

  @SuppressWarnings("unused")
  public boolean isHighBitSize() {
    return bitSize() > 64;
  }

  public String valueClassName() {
    return valueClass().getSimpleName();
  }

  public Class<?> valueClass() {
    return switch (bitSize()) {
      case 1 -> Boolean.class;
      case int bitSize when (bitSize <= 8) -> Byte.class;
      case int bitSize when (bitSize <= 16) -> Short.class;
      case int bitSize when (bitSize <= 32) -> Integer.class;
      case int bitSize when (bitSize <= 64) -> Long.class;
      case int bitSize when (bitSize <= 128) -> isSigned() ? I128.class : U128.class;
      case int bitSize when (bitSize <= 256) -> isSigned() ? I256.class : U256.class;
      case int bitSize when (bitSize <= 512) -> isSigned() ? I512.class : U512.class;
      case int bitSize when (bitSize <= 1024) -> isSigned() ? I1024.class : U1024.class;
      case int bitSize when (bitSize <= 2048) -> isSigned() ? I2048.class : U2048.class;
      default -> throw new IllegalArgumentException("Unknown type: " + typeName());
    };
  }

  public int bitSize() {
    String[] parts = splitByCharacterTypeCamelCase(typeName());
    String bitSizeString = parts[parts.length - 1];
    return isNumeric(bitSizeString) ? parseInt(bitSizeString) : 1;
  }

  public String nativePrefix() {
    return nativePrefix(className());
  }

  public Set<String> symbols() {
    return symbolsIndex().symbolsByType()
                         .values()
                         .stream()
                         .flatMap(List::stream)
                         .collect(toSet());
  }
}
