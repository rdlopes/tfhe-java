package io.github.rdlopes.tfhe.generator.templates.helpers;

import com.github.jknack.handlebars.Options;
import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.mappings.FheTypeContext;

import java.math.BigInteger;
import java.util.Map;

public record BitwiseHelper(SymbolsIndex symbolsIndex) {

  public CharSequence shiftLeft(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, lhs + ".shiftLeft(" + rhs + ".intValue())"),
      lhs + "<<" + rhs);
  }

  public CharSequence shiftRight(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, lhs + ".shiftRight(" + rhs + ".intValue())"),
      lhs + ">>" + rhs);
  }

  public CharSequence rotateLeft(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, "rotateLeft(" + lhs + ", " + rhs + ".intValue(), " + context.getBitSize() + ")"),
      lhs + "<<" + rhs);
  }

  public CharSequence rotateRight(FheTypeContext context, Options options) {
    String lhs = options.param(0);
    String rhs = options.param(1);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(BigInteger.class, "rotateRight(" + lhs + ", " + rhs + ".intValue(), " + context.getBitSize() + ")"),
      lhs + ">>" + rhs);
  }

  public CharSequence not(FheTypeContext context, Options options) {
    String fieldName = options.param(0);
    return Helpers.expression(
      context.getDataClass(),
      Map.of(
        BigInteger.class, fieldName + ".not()",
        boolean.class, "!" + fieldName),
      "~" + fieldName);
  }

  public CharSequence leadingZeros(FheTypeContext context, Options options) {
    String fieldName = options.param(0);
    return Helpers.expression(
      context.getDataClass(),
      "leadingZeros(" + fieldName + ", " + context.getBitSize() + ")");
  }

  public CharSequence leadingOnes(FheTypeContext context, Options options) {
    String fieldName = options.param(0);
    return Helpers.expression(
      context.getDataClass(),
      "leadingOnes(" + fieldName + ", " + context.getBitSize() + ")");
  }

  public CharSequence trailingZeros(FheTypeContext context, Options options) {
    String fieldName = options.param(0);
    return Helpers.expression(
      context.getDataClass(),
      "trailingZeros(" + fieldName + ", " + context.getBitSize() + ")");
  }

  public CharSequence trailingOnes(FheTypeContext context, Options options) {
    String fieldName = options.param(0);
    return Helpers.expression(
      context.getDataClass(),
      "trailingOnes(" + fieldName + ", " + context.getBitSize() + ")");
  }

}
