package io.github.rdlopes.tfhe.generator.templates;

import com.github.jknack.handlebars.Options;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.github.rdlopes.tfhe.generator.parsers.JextractIncludes.SymbolType.function;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.substringAfter;

public class TemplateHelper {
  public static String method(TemplateContext context, Options options) throws IOException {
    String methodPrefix = options.param(0);
    String methodName = options.param(1);
    String functionSuffix = options.param(2);
    String parameters = Arrays.stream(options.params)
                              .skip(3)
                              .map(Object::toString)
                              .collect(joining(", "));

    boolean override = options.hash("override", true);
    String annotation = options.hash("annotation", "");

    return javadoc(functionSuffix, options) + "\n" +
      (override ? "@Override\n" : "") +
      (annotation.isBlank() ? "" : annotation + "\n") +
      methodPrefix + " " + methodName + "(" + parameters + "){" + "\n" +
      options.fn(context) + "\n" +
      "}";
  }

  public static String symbol(String symbolPrefix, Options options) {
    TemplateContext context = (TemplateContext) options.context.model();
    return context.symbolsIndex()
                  .lookupSymbol(context.nativePrefix() + symbolPrefix);
  }

  public static String javadoc(String symbolSuffix, Options options) {
    TemplateContext context = (TemplateContext) options.context.model();
    String symbol = symbol(symbolSuffix, options);

    String definition = context.symbolsIndex()
                               .definitions()
                               .getOrDefault(symbol, "")
                               .replace("/**", " *")
                               .replace("*/", "*");
    return """
      /**
      {@snippet lang = "c":
      %s
      }
      */""".formatted(definition);
  }

  public static String declare(String objectName, Options options) {
    TemplateContext context = (TemplateContext) options.context.model();
    String className = options.hash("class", context.className());
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

  public static String executeWithAddress(String functionSuffix, String variableName, String lambdaBody, Options options) {
    String symbol = symbol(functionSuffix, options);
    return "executeWithAddress(%s.getAddress(), address -> %s(%s));".formatted(variableName, symbol, lambdaBody);
  }

  public static String trace(String functionName, Options options) {
    String parametersString = Arrays.stream(options.params)
                                    .skip(1)
                                    .map(Object::toString)
                                    .map(s -> s + ": {}")
                                    .collect(joining(", ", "- ", ""));
    String parametersList = Arrays.stream(options.params)
                                  .skip(1)
                                  .map(Object::toString)
                                  .collect(joining(", "));
    return options.params.length < 2
      ? "logger.trace(\"%s\");".formatted(functionName)
      : "logger.trace(\"%s %s\", %s);".formatted(functionName, parametersString, parametersList);
  }

  public static String decompressed(String className, Options ignoredOptions) {
    return substringAfter(className, "Compressed");
  }

  @SuppressWarnings("unused")
  public static String casts(TemplateContext context, Options options) {
    Collection<String> castMethods = context.symbolsIndex()
                                            .lookupSymbols(function, s -> s.startsWith(context.nativePrefix() + "cast_into"));

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
                                        javadoc(substringAfter(entry.getKey(), context.nativePrefix()), options),
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
