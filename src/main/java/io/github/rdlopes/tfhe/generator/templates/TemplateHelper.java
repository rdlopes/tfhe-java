package io.github.rdlopes.tfhe.generator.templates;

import com.github.jknack.handlebars.Options;
import io.github.rdlopes.tfhe.generator.Symbols;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class TemplateHelper {

  public static String implementation(FheTypeContext context, Options ignoredOptions) throws IOException {
    if (context.isBoolean()) {
      return "FheBoolean<" + context.getClassName() + ", Compressed" + context.getClassName() + ">";
    } else if (context.isSigned()) {
      return "FheInteger<" + context.valueClassName() + ", " + context.getClassName() + ", Compressed" + context.getClassName() + ">";
    }
    return "FheUnsignedInteger<" + context.valueClassName() + ", " + context.getClassName() + ", Compressed" + context.getClassName() + ">";
  }

  public static String method(FheTypeContext context, Options options) throws IOException {
    String methodPrefix = options.param(0);
    String methodName = options.param(1);
    String methodSearch = options.param(2);
    String parameters = Arrays.stream(options.params)
                              .skip(3)
                              .map(Object::toString)
                              .collect(joining(", "));

    boolean override = options.hash("override", true);
    String annotation = options.hash("annotation", "");

    return javadoc(methodSearch, options) + "\n" +
      (override ? "@Override\n" : "") +
      (annotation.isBlank() ? "" : annotation + "\n") +
      methodPrefix + " " + methodName + "(" + parameters + "){" + "\n" +
      options.fn(context) + "\n" +
      "}\n";
  }

  public static String symbol(String symbolSearch, Options options) {
    FheTypeContext context = (FheTypeContext) options.context.model();
    boolean lookupType = options.hash("lookupType", false);
    boolean prefixed = options.hash("prefixed", true);
    String prefix = "";
    if (prefixed) {
      prefix = lookupType
        ? FheTypeContext.nativeType(context.getTypeName())
        : context.nativeType();
      prefix += "_";
    }
    return context.getSymbols()
                  .lookupSymbol(prefix + symbolSearch);
  }

  @SuppressWarnings("unused")
  public static String lookupDoc(Symbols symbols, String symbol, Options ignored) {
    String definition = symbols.definitions()
                               .get(symbol);
    return javadocText(definition);
  }

  public static String javadoc(String symbolSearch, Options options) {
    FheTypeContext context = (FheTypeContext) options.context.model();
    String symbol = symbol(symbolSearch, options);

    String definition = context.getSymbols()
                               .definitions()
                               .get(symbol);

    return javadocText(definition);
  }

  private static String javadocText(String definition) {
    return definition == null
      ? "/// Not implemented"
      : definition.lines()
                  .map(line -> "/// " + line)
                  .collect(joining("\n", "/// ```c\n", "\n/// ```"));
  }

  public static String declare(String objectName, Options options) {
    FheTypeContext context = (FheTypeContext) options.context.model();
    String className = options.hash("class", context.getClassName());
    return "%s %s = new %s();".formatted(className, objectName, className);
  }

  public static String execute(String functionSuffix, String lambdaBody, Options options) {
    String symbol = symbol(functionSuffix, options);
    return "execute(() -> %s(%s));".formatted(symbol, lambdaBody);
  }

  public static String executeAndReturn(String functionSuffix, String returnedClassName, String lambdaBody, Options options) {
    String symbol = symbol(functionSuffix, options);
    return "executeAndReturn(%s.class, address -> %s(%s));".formatted(returnedClassName, symbol, lambdaBody);
  }

  public static String trace(String functionName, Options options) {
    String parametersString = Arrays.stream(options.params)
                                    .map(Object::toString)
                                    .map(s -> s + ": {}")
                                    .collect(joining(", ", "- ", ""));
    String parametersList = Arrays.stream(options.params)
                                  .map(Object::toString)
                                  .collect(joining(", "));
    return options.params.length == 0
      ? "logger.trace(\"%s\");".formatted(functionName)
      : "logger.trace(\"%s %s\", %s);".formatted(functionName, parametersString, parametersList);
  }

  @SuppressWarnings("unused")
  public static String casts(FheTypeContext context, Options options) {
    Collection<String> castMethods = context.getSymbols()
                                            .lookupSymbols(s -> s.startsWith(context.nativeType() + "_cast_into"));

    List<String> casts = castMethods.stream()
                                    .map(s ->
                                      Map.entry(s, Arrays.stream(substringAfter(s, "cast_into_").split("_"))
                                                         .map(StringUtils::capitalize)
                                                         .collect(joining())))
                                    .map(entry -> """
                                        %s
                                        public %s castInto%s() {
                                          %s result = new %s();
                                          execute(() -> %s(getValue(), result.getAddress()));
                                          return result;
                                        }
                                        """.formatted(
                                      javadoc(substringAfter(entry.getKey(), context.nativeType() + "_"), options),
                                        entry.getValue(),
                                        entry.getValue(),
                                        entry.getValue(),
                                        entry.getValue(),
                                        entry.getKey()
                                      )
                                    )
                                    .toList();

    return String.join("\n", casts);
  }

}
