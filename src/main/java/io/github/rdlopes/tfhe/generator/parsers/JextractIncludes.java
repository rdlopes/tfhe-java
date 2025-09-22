package io.github.rdlopes.tfhe.generator.parsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public record JextractIncludes(SortedMap<SymbolType, List<String>> symbolsByType) {
  private static final Pattern INCLUDES_PATTERN = Pattern.compile("^--include-(.+?) (.+?)\\s*#.+$");

  public static JextractIncludes from(Path path) throws IOException {
    try (Stream<String> lines = Files.lines(path)) {

      Map<String, List<Matcher>> symbols =
        lines.map(INCLUDES_PATTERN::matcher)
             .filter(Matcher::matches)
             .collect(groupingBy(m -> m.group(1)));

      Map<SymbolType, List<String>> symbolsByType =
        symbols.entrySet()
               .stream()
               .collect(toMap(
                 entry -> SymbolType.valueOf(entry.getKey()),
                 entry -> entry.getValue()
                               .stream()
                               .map(m -> m.group(2))
                               .map(String::trim)
                               .toList()));

      return new JextractIncludes(new TreeMap<>(symbolsByType));
    }
  }

  public enum SymbolType {
    struct, var, function, typedef, union, constant
  }
}
