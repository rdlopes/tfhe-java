package io.github.rdlopes.tfhe.utils;

import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NonNull;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static java.util.stream.Collectors.joining;

public interface FheType {

  @NonNull
  String packageName();

  @NonNull
  String name();

  @NonNull
  Collection<String> methods();

  @NonNull
  Map<String, String> definitions();

  default String getPrimaryTestValue() {
    return switch (dataClass().getSimpleName()) {
      case "Byte" -> "(byte) 100";
      case "Short" -> "(short) 100";
      case "Integer" -> "100";
      case "Long" -> "100L";
      case "BigInteger" -> "BigInteger.valueOf(100)";
      default -> "true";
    };
  }

  default String getSecondaryTestValue() {
    return switch (dataClass().getSimpleName()) {
      case "Byte" -> "(byte) 50";
      case "Short" -> "(short) 50";
      case "Integer" -> "50";
      case "Long" -> "50L";
      case "BigInteger" -> "BigInteger.valueOf(50)";
      default -> "false";
    };
  }

  default String getTestScalarValue() {
    return switch (dataClass().getSimpleName()) {
      case "Byte" -> "(byte) 7";
      case "Short" -> "(short) 7";
      case "Integer" -> "7";
      case "Long" -> "7L";
      case "BigInteger" -> "BigInteger.valueOf(7)";
      default -> "true";
    };
  }

  default String getExpectedAddResult() {
    return switch (dataClass().getSimpleName()) {
      case "Byte" -> "(byte) 150";
      case "Short" -> "(short) 150";
      case "Integer" -> "150";
      case "Long" -> "150L";
      case "BigInteger" -> "BigInteger.valueOf(150)";
      default -> "true";
    };
  }

  default String getExpectedSubResult() {
    return switch (dataClass().getSimpleName()) {
      case "Byte" -> "(byte) 50";
      case "Short" -> "(short) 50";
      case "Integer" -> "50";
      case "Long" -> "50L";
      case "BigInteger" -> "BigInteger.valueOf(50)";
      default -> "false";
    };
  }

  default String getExpectedMulResult() {
    return switch (dataClass().getSimpleName()) {
      case "Byte" -> "(byte) 96";
      case "Short" -> "(short) 5000";
      case "Integer" -> "5000";
      case "Long" -> "5000L";
      case "BigInteger" -> "BigInteger.valueOf(5000)";
      default -> "false";
    };
  }

  default String getExpectedAndResult() {
    return switch (dataClass().getSimpleName()) {
      case "Byte" -> "(byte) 32";
      case "Short" -> "(short) 32";
      case "Integer" -> "32";
      case "Long" -> "32L";
      case "BigInteger" -> "BigInteger.valueOf(32)";
      default -> "false";
    };
  }

  default String getExpectedOrResult() {
    return switch (dataClass().getSimpleName()) {
      case "Byte" -> "(byte) 118";
      case "Short" -> "(short) 118";
      case "Integer" -> "118";
      case "Long" -> "118L";
      case "BigInteger" -> "BigInteger.valueOf(118)";
      default -> "true";
    };
  }

  default String getExpectedXorResult() {
    return switch (dataClass().getSimpleName()) {
      case "Byte" -> "(byte) 86";
      case "Short" -> "(short) 86";
      case "Integer" -> "86";
      case "Long" -> "86L";
      case "BigInteger" -> "BigInteger.valueOf(86)";
      default -> "true";
    };
  }

  default String getExpectedScalarAddResult() {
    return switch (dataClass().getSimpleName()) {
      case "Byte" -> "(byte) 107";
      case "Short" -> "(short) 107";
      case "Integer" -> "107";
      case "Long" -> "107L";
      case "BigInteger" -> "BigInteger.valueOf(107)";
      default -> "true";
    };
  }

  default String getDataValue(String variable) {
    if (needsUtilityClass()) {
      return utilityClass() + ".valueOf(" + variable + ").getAddress()";
    } else {
      return variable;
    }
  }

  default int bitSize() {
    String[] parts = StringUtils.splitByCharacterTypeCamelCase(name());
    return Integer.parseInt(parts[parts.length - 1]);
  }

  @NonNull
  default Class<?> dataClass() {
    return switch (bitSize()) {
      case int bitSize when (bitSize <= 8) -> Byte.class;
      case int bitSize when (bitSize <= 16) -> Short.class;
      case int bitSize when (bitSize <= 32) -> Integer.class;
      case int bitSize when (bitSize <= 64) -> Long.class;
      default -> BigInteger.class;
    };
  }

  @NonNull
  default String layout() {
    return switch (bitSize()) {
      case int bitSize when (bitSize <= 8) -> "C_CHAR";
      case int bitSize when (bitSize <= 16) -> "C_SHORT";
      case int bitSize when (bitSize <= 32) -> "C_INT";
      case int bitSize when (bitSize <= 64) -> "C_LONG_LONG";
      default -> "C_POINTER";
    };
  }

  default String nativeName() {
    String[] parts = StringUtils.splitByCharacterTypeCamelCase(name());
    String nativeName = Arrays.stream(parts)
                              .map(String::toLowerCase)
                              .takeWhile(StringUtils::isAlpha)
                              .collect(joining("_"));
    nativeName += parts[parts.length - 1];
    return nativeName;
  }

  default boolean exists() {
    return !methods().isEmpty();
  }

  @NonNull
  default String method(@NonNull String searchString) {
    return methods().stream()
                    .filter(method -> method.contains(searchString))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No method found containing: " + searchString));
  }

  default String utilityClass() {
    if (!needsUtilityClass()) {
      return "";
    }
    String prefix = name().startsWith("FheUint") ? "U" : "I";
    return switch (bitSize()) {
      case int bitSize when (bitSize <= 128) -> prefix + "128";
      case int bitSize when (bitSize <= 256) -> prefix + "256";
      case int bitSize when (bitSize <= 512) -> prefix + "512";
      case int bitSize when (bitSize <= 1024) -> prefix + "1024";
      case int bitSize when (bitSize <= 2048) -> prefix + "2048";
      default -> "";
    };
  }

  default boolean needsUtilityClass() {
    return dataClass() == BigInteger.class;
  }

  record FheIntType(
    String packageName,
    String name,
    Map<String, String> definitions,
    Collection<String> methods) implements FheType {
  }

  record CompressedFheIntType(
    String packageName,
    String name,
    Map<String, String> definitions,
    Collection<String> methods) implements FheType {
  }

  record FheBoolType(
    String packageName,
    String name,
    Map<String, String> definitions,
    Collection<String> methods) implements FheType {

    @Override
    public int bitSize() {
      return 1;
    }

    @Override
    @NonNull
    public Class<?> dataClass() {
      return Boolean.class;
    }

    @Override
    @NonNull
    public String layout() {
      return "C_BOOL";
    }

    @Override
    public String nativeName() {
      return "fhe_bool";
    }

    @Override
    public boolean needsUtilityClass() {
      return false;
    }

    @Override
    public String getExpectedAndResult() {
      return getPrimaryTestValue() + " && " + getSecondaryTestValue();
    }

    @Override
    public String getExpectedOrResult() {
      return getPrimaryTestValue() + " || " + getSecondaryTestValue();
    }

    @Override
    public String getExpectedXorResult() {
      return getPrimaryTestValue() + " ^ " + getSecondaryTestValue();
    }
  }

  record CompressedFheBoolType(
    String packageName,
    String name,
    Map<String, String> definitions,
    Collection<String> methods) implements FheType {

    @Override
    public int bitSize() {
      return 1;
    }

    @Override
    @NonNull
    public Class<?> dataClass() {
      return Boolean.class;
    }

    @Override
    @NonNull
    public String layout() {
      return "C_BOOL";
    }

    @Override
    public String nativeName() {
      return "compressed_fhe_bool";
    }

    @Override
    public boolean needsUtilityClass() {
      return false;
    }

    @Override
    public String getExpectedAndResult() {
      return getPrimaryTestValue() + " && " + getSecondaryTestValue();
    }

    @Override
    public String getExpectedOrResult() {
      return getPrimaryTestValue() + " || " + getSecondaryTestValue();
    }

    @Override
    public String getExpectedXorResult() {
      return getPrimaryTestValue() + " ^ " + getSecondaryTestValue();
    }
  }
}
