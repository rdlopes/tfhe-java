package io.github.rdlopes.tfhe.utils;

import io.github.rdlopes.tfhe.api.values.*;

import java.util.Arrays;

import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase;

public final class FheUtils {

  private FheUtils() {
  }

  public static boolean isUnsigned(String typeName) {
    return typeName.startsWith("FheUint");
  }

  public static boolean isSigned(String typeName) {
    return !isUnsigned(typeName);
  }

  public static boolean isBool(String typeName) {
    return typeName.equals("FheBool");
  }

  public static boolean hasHighBitSize(String typeName) {
    return bitSize(typeName) > 64;
  }

  public static String nativeType(String typeName) {
    return Arrays.stream(splitByCharacterTypeCamelCase(typeName))
                 .map(String::toLowerCase)
                 .reduce("", (acc, part) -> acc + (acc.isBlank() || isNumeric(part) ? "" : "_") + part);
  }

  public static Class<?> valueClass(String typeName) {
    return switch (bitSize(typeName)) {
      case 1 -> Boolean.class;
      case int bitSize when (bitSize <= 8) -> Byte.class;
      case int bitSize when (bitSize <= 16) -> Short.class;
      case int bitSize when (bitSize <= 32) -> Integer.class;
      case int bitSize when (bitSize <= 64) -> Long.class;
      case int bitSize when (bitSize <= 128) -> isSigned(typeName) ? I128.class : U128.class;
      case int bitSize when (bitSize <= 256) -> isSigned(typeName) ? I256.class : U256.class;
      case int bitSize when (bitSize <= 512) -> isSigned(typeName) ? I512.class : U512.class;
      case int bitSize when (bitSize <= 1024) -> isSigned(typeName) ? I1024.class : U1024.class;
      case int bitSize when (bitSize <= 2048) -> isSigned(typeName) ? I2048.class : U2048.class;
      default -> throw new IllegalArgumentException("Unknown type: " + typeName);
    };
  }

  public static String valueClassName(String typeName) {
    return valueClass(typeName).getSimpleName();
  }

  public static int bitSize(String typeName) {
    String[] parts = splitByCharacterTypeCamelCase(typeName);
    String bitSizeString = parts[parts.length - 1];
    return isNumeric(bitSizeString)
      ? parseInt(bitSizeString)
      : isBool(typeName) ? 1 : 8;
  }

  public static int bitLength(String typeName) {
    int bitSize = bitSize(typeName);
    return switch (bitSize) {
      case 1 -> 1;
      case int size when (size <= 8) -> 8;
      case int size when (size <= 16) -> 16;
      case int size when (size <= 32) -> 32;
      case int size when (size <= 64) -> 64;
      case int size when (size <= 128) -> 128;
      case int size when (size <= 256) -> 256;
      case int size when (size <= 512) -> 512;
      case int size when (size <= 1024) -> 1024;
      case int size when (size <= 2048) -> 2048;
      default -> throw new IllegalArgumentException("Unknown type: " + typeName);
    };
  }

}
