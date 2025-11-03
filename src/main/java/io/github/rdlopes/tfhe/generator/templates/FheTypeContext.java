package io.github.rdlopes.tfhe.generator.templates;

import io.github.rdlopes.tfhe.api.values.*;
import io.github.rdlopes.tfhe.generator.Symbols;
import io.github.rdlopes.tfhe.generator.TemplateContext;

import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.*;

public final class FheTypeContext extends TemplateContext {
  private final String typeName;

  public FheTypeContext(String templateName, String packageName, String className, String typeName, Symbols symbols) {
    super(templateName, packageName, className, symbols);
    this.typeName = typeName;
  }

  public static FheTypeContext forType(String packageName, String fheType, Symbols symbols) {
    return new FheTypeContext("FheType", packageName, fheType, fheType, symbols.withFilter(s ->
      s.equals(fheType)
        || s.startsWith(nativeType(fheType) + "_")
        || (s.startsWith("generate_oblivious") && s.endsWith(nativeType(fheType)))
    ));
  }

  public static String nativeType(String type) {
    String prefixed = Arrays.stream(splitByCharacterTypeCamelCase(type))
                            .map(String::toLowerCase)
                            .map(s -> isAlpha(s) ? "_" + s : s)
                            .collect(joining());
    return substringAfter(prefixed, "_");
  }

  public static FheTypeContext forCompressedType(String packageName, String fheType, Symbols symbols) {
    String className = "Compressed" + fheType;
    return new FheTypeContext("CompressedFheType", packageName, className, fheType, symbols.withFilter(s -> s.equals(className) || s.startsWith(nativeType(className))));
  }

  public static FheTypeContext forArrayType(String packageName, String fheType, Symbols symbols) {
    String className = fheType + "Array";
    return new FheTypeContext("FheTypeArray", packageName, className, fheType, symbols.withFilter(s -> s.equals(fheType) || s.startsWith(nativeType(fheType))));
  }

  public String getTypeName() {
    return typeName;
  }

  public boolean isBoolean() {
    return getClassName().startsWith("FheBool");
  }

  public boolean isInteger() {
    return getClassName().startsWith("FheInt") || getClassName().startsWith("FheUint");
  }

  public boolean isSigned() {
    return !getClassName().contains("Uint");
  }

  public boolean hasArray() {
    return getSymbols().lookupSymbol(nativeType(getTypeName() + "_array")) != null;
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
      default -> throw new IllegalArgumentException("Unknown type: " + getTypeName());
    };
  }

  public int bitSize() {
    String[] parts = splitByCharacterTypeCamelCase(getTypeName());
    String bitSizeString = parts[parts.length - 1];
    return isNumeric(bitSizeString) ? parseInt(bitSizeString) : 1;
  }

  public String nativeType() {
    return nativeType(getClassName());
  }

}
