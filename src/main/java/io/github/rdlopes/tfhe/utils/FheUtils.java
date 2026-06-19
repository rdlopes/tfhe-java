package io.github.rdlopes.tfhe.utils;

import io.github.rdlopes.tfhe.api.values.*;
import io.github.rdlopes.tfhe.api.values.extended.*;

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
  
  private static final int[] BUCKETS = {1, 8, 16, 32, 64, 128, 256, 512, 1024, 2048};
  
  public static Class<?> valueClass(String typeName) {
    boolean signed = isSigned(typeName);
    return switch (bitLength(typeName)) {
      case 1 -> Boolean.class;
      case 8 -> Byte.class;
      case 16 -> Short.class;
      case 32 -> Integer.class;
      case 64 -> Long.class;
      case 128 -> signed ? I128.class : U128.class;
      case 256 -> signed ? I256.class : U256.class;
      case 512 -> signed ? I512.class : U512.class;
      case 1024 -> signed ? I1024.class : U1024.class;
      case 2048 -> signed ? I2048.class : U2048.class;
      default -> throw new IllegalArgumentException("Unknown type: " + typeName);
    };
  }

  public static String valueClassName(String typeName) {
    return valueClass(typeName).getSimpleName();
  }

  public static int bitSize(String typeName) {
    String[] parts = splitByCharacterTypeCamelCase(typeName);
    String bitSizeString = parts[parts.length - 1];
    if (isNumeric(bitSizeString)) {
      return parseInt(bitSizeString);
    }
    return isBool(typeName) ? 1 : 8;
  }

  public static int bitLength(String typeName) {
    int bitSize = bitSize(typeName);
    for (int bucket : BUCKETS) {
      if (bitSize <= bucket) {
        return bucket;
      }
    }
    throw new IllegalArgumentException("Unknown type: " + typeName);
  }

}
