package io.github.rdlopes.tfhe.generator;

import java.util.SortedMap;
import java.util.SortedSet;
import java.util.stream.Stream;

import static io.github.rdlopes.tfhe.generator.parsers.JextractIncludesFile.Type;

public record SymbolsIndex(SortedSet<String> names,
                           SortedMap<String, Type> types,
                           SortedMap<String, String> definitions) {
  public SymbolsIndex {
    if (names.stream()
             .anyMatch(name -> !types.containsKey(name) || !definitions.containsKey(name))) {
      throw new IllegalStateException("SymbolsIndex is not complete");
    }
  }

  public String prettyPrint() {
    StringBuilder result = new StringBuilder("SymbolsIndex - " + names.size() + " symbols available:\n");
    names.forEach(name -> result.append("- %s %s".formatted(types.get(name), name)
                                                 .indent(2)));
    return result.toString();
  }

  public Stream<String> fheTypes() {
    String typePrefix = "Type_";
    return names().stream()
                  .filter(name -> name.startsWith(typePrefix))
                  .map(name -> name.substring(typePrefix.length()));
  }

  public Stream<String> fheKeys() {
    String keyDestroySuffix = "_key_destroy";
    return names().stream()
                  .filter(name -> name.endsWith(keyDestroySuffix))
                  .map(name -> name.replace("_destroy", ""));
  }
}
