package io.github.rdlopes.tfhe.generator.parsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public record JextractIncludesFile(SortedMap<String, Type> includes) {
  private static final Pattern INCLUDES_PATTERN = Pattern.compile("^--include-(.+?) (.+?)\\s*#.+$");

  public static JextractIncludesFile from(Path path) throws IOException {
    SortedMap<String, Type> includes = new TreeMap<>();
    try (Stream<String> lines = Files.lines(path)) {
      lines.map(INCLUDES_PATTERN::matcher)
           .filter(Matcher::matches)
           .forEach(matcher -> {
             String type = matcher.group(1);
             String symbol = matcher.group(2);
             includes.put(symbol.trim(), Type.valueOf(type));
           });
    }
    return new JextractIncludesFile(includes);
  }

  public boolean containsSymbol(String symbol) {
    return includes.containsKey(symbol);
  }

  public Type getType(String symbol) {
    return includes.getOrDefault(symbol, Type.unknown);
  }

  public enum Type {
    unknown, function, constant, struct, union, typedef, var
  }
}
