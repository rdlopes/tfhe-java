package io.github.rdlopes.tfhe.generator.templates.helpers;

import com.github.jknack.handlebars.Options;
import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappings.FheTypeContext;

import java.math.BigInteger;

import static io.github.rdlopes.tfhe.generator.templates.helpers.Helpers.text;

public record TestHelper(SymbolsIndex symbolsIndex) {

  private String valueString(FheTypeContext context, BigInteger value) {
    Class<?> dataClass = context.getDataClass();

    if (dataClass == boolean.class) {
      return value.equals(BigInteger.ZERO) ? "false" : "true";
    } else if (dataClass == byte.class) {
      return "(byte) " + value.byteValue();
    } else if (dataClass == short.class) {
      return "(short) " + value.shortValue();
    } else if (dataClass == int.class) {
      return String.valueOf(value.intValue());
    } else if (dataClass == long.class) {
      return value.longValue() + "L";
    } else {
      return "new BigInteger(\"" + value + "\")";
    }
  }

  public CharSequence testValue(FheTypeContext context, Options options) {
    BigInteger value = context.getBitSize() <= 8
      ? BigInteger.ONE
      : BigInteger.valueOf(42);
    return text(
      "%s originalValue = %s;".formatted(
        context.getDataClass()
               .getSimpleName(),
        valueString(context, value)));
  }

  public CharSequence testSet(FheTypeContext context, Options options) {
    BigInteger lhs = context.getBitSize() <= 8
      ? BigInteger.ONE
      : BigInteger.valueOf(42);
    BigInteger rhs = context.getBitSize() <= 8
      ? BigInteger.ONE
      : BigInteger.TWO;
    BigInteger shift = BigInteger.ONE;

    if (context.getClassName()
               .equals("FheInt2")) {
      lhs = BigInteger.ZERO;
    }

    return text("""
      %s lhs = %s;
      %s rhs = %s;
      %s shift = %s;
      """.formatted(
      context.getDataClass()
             .getSimpleName(),
      valueString(context, lhs),
      context.getDataClass()
             .getSimpleName(),
      valueString(context, rhs),
      context.getDataClass()
             .getSimpleName(),
      valueString(context, shift)));
  }

}
