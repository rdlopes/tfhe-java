package io.github.rdlopes.tfhe.generator.templates.helpers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static com.github.jknack.handlebars.Handlebars.SafeString;
import static org.apache.commons.lang3.StringUtils.stripEnd;

public class Helpers {
  public static CharSequence text(String content) {
    return new SafeString(stripEnd(content, "\n"));
  }

  public static CharSequence number(String content, Class<?> numberClass) {
    String stripped = stripEnd(content, "\n");
    if (numberClass == byte.class || numberClass == Byte.class) {
      return new SafeString("(byte) (" + stripped + ")");
    } else if (numberClass == short.class || numberClass == Short.class) {
      return new SafeString("(short) (" + stripped + ")");
    } else if (numberClass == int.class || numberClass == Integer.class) {
      return new SafeString("(int) (" + stripped + ")");
    } else if (numberClass == long.class || numberClass == Long.class) {
      return new SafeString("(long) (" + stripped + ")");
    } else if (numberClass == float.class || numberClass == Float.class) {
      return new SafeString("(float) (" + stripped + ")");
    } else if (numberClass == double.class || numberClass == Double.class) {
      return new SafeString("(double) (" + stripped + ")");
    } else if (numberClass == BigDecimal.class) {
      if (isJavaExpression(stripped)) {
        return new SafeString("(" + stripped + ")");
      }
      return new SafeString("new BigDecimal(\"" + stripped + "\")");
    } else if (numberClass == BigInteger.class) {
      if (isJavaExpression(stripped)) {
        return new SafeString("(" + stripped + ")");
      }
      return new SafeString("new BigInteger(\"" + stripped + "\")");
    } else if (numberClass == boolean.class || numberClass == Boolean.class) {
      return new SafeString("(" + stripped + ")");
    }
    return new SafeString(stripped);
  }

  private static boolean isJavaExpression(String content) {
    return content.contains(".") && (content.contains("(") || content.contains("min") || content.contains("max") ||
      content.contains("add") || content.contains("subtract") || content.contains("multiply") ||
      content.contains("divide") || content.contains("remainder") || content.contains("compareTo"));
  }

  public static CharSequence expression(Class<?> dataClass, Class<?> expressionClass, Map<Class<?>, String> expressions, String defaultExpression) {
    String content = expressions.getOrDefault(dataClass, defaultExpression);
    return Helpers.number(content, expressionClass);
  }

  public static CharSequence expression(Class<?> expressionClass, String defaultExpression) {
    return Helpers.number(defaultExpression, expressionClass);
  }

  public static CharSequence expression(Class<?> dataClass, Map<Class<?>, String> expressions, String defaultExpression) {
    return expression(dataClass, dataClass, expressions, defaultExpression);
  }

}
