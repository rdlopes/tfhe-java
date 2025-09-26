package io.github.rdlopes.tfhe.generator.parsers;

import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static io.github.rdlopes.tfhe.generator.parsers.JextractIncludes.SymbolType;
import static io.github.rdlopes.tfhe.generator.parsers.JextractIncludes.from;
import static java.util.regex.Pattern.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public record SymbolsIndex(
  SortedMap<SymbolType, List<String>> symbolsByType,
  SortedMap<String, String> definitions,
  SortedSet<String> used) {

  public static SymbolsIndex parse(Path nativeHeaderPath, Path jextractIncludesPath) throws IOException {
    NativeHeaderFile nativeHeader = NativeHeaderFile.from(nativeHeaderPath);
    JextractIncludes jextractIncludes = from(jextractIncludesPath);

    Map<String, String> definitions = jextractIncludes.symbolsByType()
                                                      .values()
                                                      .stream()
                                                      .flatMap(Collection::stream)
                                                      .collect(toMap(
                                                        symbol -> symbol,
                                                        symbol -> transformDefinitionOf(symbol, nativeHeader.lookup(symbol))
                                                      ));

    return new SymbolsIndex(jextractIncludes.symbolsByType(), new TreeMap<>(definitions), new TreeSet<>());
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

  public SymbolsIndex withFilter(Predicate<String> predicate) {
    Map<SymbolType, List<String>> symbolsByType = symbolsByType()
      .entrySet()
      .stream()
      .collect(toMap(
        Map.Entry::getKey,
        entry -> entry.getValue()
                      .stream()
                      .filter(predicate)
                      .toList()
      ));

    Map<String, String> definitions = definitions()
      .entrySet()
      .stream()
      .filter(e -> predicate.test(e.getKey()))
      .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));

    return new SymbolsIndex(new TreeMap<>(symbolsByType), new TreeMap<>(definitions), new TreeSet<>());
  }

  @Override
  public @NonNull String toString() {
    return "SymbolsIndex" + symbolsByType.entrySet()
                                         .stream()
                                         .map(entry -> "{" + entry.getKey() + ": " + entry.getValue() + "}")
                                         .collect(joining("\n"));
  }

  public Collection<String> lookupSymbols(SymbolType symbolType, Predicate<String> predicate) {
    List<String> foundSymbols = symbolsByType.get(symbolType)
                                             .stream()
                                             .filter(predicate)
                                             .toList();
    used().addAll(foundSymbols);

    return foundSymbols;
  }

  public String lookupSymbol(String symbolName) {
    return symbolsByType.values()
                        .stream()
                        .flatMap(Collection::stream)
                        .filter(s -> s.startsWith(symbolName))
                        .findFirst()
                        .orElse(null);
  }

  public SortedSet<String> unused() {
    List<String> symbolNames = symbolsByType.values()
                                            .stream()
                                            .flatMap(List::stream)
                                            .toList();
    List<String> unusedSymbols = new ArrayList<>(symbolNames);
    unusedSymbols.removeAll(used());

    return new TreeSet<>(unusedSymbols);
  }
}
