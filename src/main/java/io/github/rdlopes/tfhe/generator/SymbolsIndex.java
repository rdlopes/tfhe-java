package io.github.rdlopes.tfhe.generator;

import org.jspecify.annotations.NonNull;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record SymbolsIndex(SortedSet<String> names,
                           SortedMap<String, String> definitions,
                           SortedSet<String> symbolsUsed) {

  public SymbolsIndex(SortedSet<String> names, SortedMap<String, String> definitions) {
    this(names, definitions, new TreeSet<>());
  }

  public SymbolsIndex {
    if (names.stream()
             .anyMatch(name -> !definitions.containsKey(name))) {
      throw new IllegalStateException("SymbolsIndex is not complete");
    }
  }

  public SymbolsIndex filtered(Predicate<String> filter) {
    Collection<String> newNames = names.stream()
                                       .filter(filter)
                                       .toList();
    Map<String, String> newDefinitions = definitions.entrySet()
                                                    .stream()
                                                    .filter(e -> filter.test(e.getKey()))
                                                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    Collection<String> newSymbolsUsed = symbolsUsed.stream()
                                                   .filter(filter)
                                                   .toList();
    return new SymbolsIndex(new TreeSet<>(newNames),
      new TreeMap<>(newDefinitions),
      new TreeSet<>(newSymbolsUsed));
  }

  @Override
  public @NonNull String toString() {
    return "SymbolsIndex{" + names + '}';
  }

  public String lookupPrefixed(String symbolPrefix) {
    String symbol = names.contains(symbolPrefix)
      ? symbolPrefix
      : names.stream()
             .filter(n -> n.startsWith(symbolPrefix))
             .findFirst()
             .orElseThrow(() -> new IllegalArgumentException("No native method found for " + symbolPrefix));
    symbolsUsed.add(symbol);
    return symbol;
  }

  public Stream<String> lookupFiltered(Predicate<String> filter) {
    return names.stream()
                .filter(filter)
                .peek(symbolsUsed::add);
  }

  public String lookupDefinition(String symbol) {
    symbolsUsed.add(symbol);
    return definitions.getOrDefault(symbol, "");
  }

  public Collection<String> getUnusedSymbols() {
    Collection<String> unusedSymbols = new TreeSet<>(names);
    unusedSymbols.removeAll(symbolsUsed);
    return unusedSymbols;
  }
}
