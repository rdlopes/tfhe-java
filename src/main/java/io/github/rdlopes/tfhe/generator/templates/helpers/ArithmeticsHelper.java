package io.github.rdlopes.tfhe.generator.templates.helpers;

import com.github.jknack.handlebars.Options;
import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappings.FheTypeContext;

import java.math.BigInteger;
import java.util.Map;

public record ArithmeticsHelper(SymbolsIndex symbolsIndex) {

  public CharSequence add(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, lhs + ".add(" + rhs + ")"),
      lhs + "+" + rhs
    );
  }

  public CharSequence multiply(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, lhs + ".multiply(" + rhs + ")"),
      lhs + "*" + rhs
    );
  }

  public CharSequence divide(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, lhs + ".divide(" + rhs + ")"),
      lhs + "/" + rhs);
  }

  public CharSequence remainder(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, lhs + ".remainder(" + rhs + ")"),
      lhs + "%" + rhs);
  }

  public CharSequence subtract(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, lhs + ".subtract(" + rhs + ")"),
      lhs + "-" + rhs);
  }

  public CharSequence negate(FheTypeContext context, Options options) {
    String fieldName = options.param(0);
    String defaultExpression = "(-" + fieldName + ")";
    if (!context.isSigned()) {
      defaultExpression = "(" + fieldName + "|1<<" + context.getBitSize() + ")";
    }
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, fieldName + ".negate()"),
      defaultExpression);
  }

  public CharSequence ilog2(FheTypeContext context, Options options) {
    String fieldName = options.param(0);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, "BigInteger.valueOf(" + fieldName + ".bitLength())"),
      "Math.floor(Math.log(" + fieldName + ")/Math.log(2))"
    );
  }

}
