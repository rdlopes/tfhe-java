package io.github.rdlopes.tfhe.generator;

import io.github.rdlopes.tfhe.generator.parsers.JextractIncludes;
import io.github.rdlopes.tfhe.generator.parsers.NativeHeaderFile;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static io.github.rdlopes.tfhe.generator.parsers.JextractIncludes.from;
import static java.util.regex.Pattern.*;
import static java.util.stream.Collectors.toMap;

public record Symbols(SortedMap<String, String> definitions, SortedSet<String> used) {

  public static Symbols parse(Path nativeHeaderPath, Path jextractIncludesPath) throws IOException {
    NativeHeaderFile nativeHeader = NativeHeaderFile.from(nativeHeaderPath);
    JextractIncludes jextractIncludes = from(jextractIncludesPath);

    Map<String, String> definitions = jextractIncludes.symbolsByType()
                                                      .values()
                                                      .stream()
                                                      .flatMap(Collection::stream)
                                                      .collect(toMap(symbol -> symbol, symbol -> transformDefinitionOf(symbol, nativeHeader.lookup(symbol))));

    return new Symbols(new TreeMap<>(definitions), new TreeSet<>());
  }

  private static String transformDefinitionOf(String symbol, String originalDefinition) {
    String definition = originalDefinition;
    if (originalDefinition.contains("enum")) {
      definition = Pattern.compile("\\{.+\\n.+?(" + quote(symbol) + ")", MULTILINE | DOTALL)
                          .matcher(definition)
                          .replaceAll("""
                            {
                              //...
                              $1""");
      definition = Pattern.compile("[^\\n]+?(" + quote(symbol) + ".+?)\\n.+}", MULTILINE | DOTALL)
                          .matcher(definition)
                          .replaceAll("""
                              $1
                              //...
                            }""");
    }

    return StringUtils.strip(definition);
  }

  public Symbols withFilter(Predicate<String> predicate) {
    Map<String, String> definitions = definitions().entrySet()
                                                   .stream()
                                                   .filter(e -> predicate.test(e.getKey()))
                                                   .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

    return new Symbols(new TreeMap<>(definitions), new TreeSet<>());
  }

  @Override
  public @NonNull String toString() {
    return "SymbolsIndex " + definitions().keySet();
  }

  public Collection<String> lookupSymbols(Predicate<String> predicate) {
    List<String> foundSymbols = definitions.keySet()
                                           .stream()
                                           .filter(predicate)
                                           .toList();
    used().addAll(foundSymbols);

    return foundSymbols;
  }

  public String lookupSymbol(String symbolName) {
    return definitions.keySet()
                      .stream()
                      .filter(s -> s.startsWith(symbolName))
                      .peek(symbol -> used().add(symbol))
                      .findFirst()
                      .orElse(null);
  }

  public Set<String> set() {
    return new HashSet<>(definitions.keySet());
  }
}
