package io.github.rdlopes.tfhe.generator.templates.helpers;

import com.github.jknack.handlebars.Options;
import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappings.FheTypeContext;

import java.math.BigInteger;
import java.util.Map;

public record ComparisonHelper(SymbolsIndex symbolsIndex) {

  public CharSequence equal(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      boolean.class,
      Map.of(BigInteger.class, lhs + ".equals(" + rhs + ")"),
      lhs + "==" + rhs);
  }

  public CharSequence notEqual(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      boolean.class,
      Map.of(BigInteger.class, "!" + lhs + ".equals(" + rhs + ")"),
      lhs + "!=" + rhs);
  }

  public CharSequence gt(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      boolean.class,
      Map.of(BigInteger.class, lhs + ".compareTo(" + rhs + ") > 0"),
      lhs + ">" + rhs);
  }

  public CharSequence ge(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      boolean.class,
      Map.of(BigInteger.class, lhs + ".compareTo(" + rhs + ") >= 0"),
      lhs + ">=" + rhs);
  }

  public CharSequence lt(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      boolean.class,
      Map.of(BigInteger.class, lhs + ".compareTo(" + rhs + ") < 0"),
      lhs + "<" + rhs);
  }

  public CharSequence le(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      boolean.class,
      Map.of(BigInteger.class, lhs + ".compareTo(" + rhs + ") <= 0"),
      lhs + "<=" + rhs);
  }

  public CharSequence min(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, lhs + ".min(" + rhs + ")"),
      "Math.min(" + lhs + ", " + rhs + ")");
  }

  public CharSequence max(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, lhs + ".max(" + rhs + ")"),
      "Math.max(" + lhs + ", " + rhs + ")");
  }

}
