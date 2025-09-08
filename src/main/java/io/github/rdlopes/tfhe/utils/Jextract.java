package io.github.rdlopes.tfhe.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static io.github.rdlopes.tfhe.utils.JextractSymbols.Type;

public class Jextract {
  public static final Pattern INCLUDES_PATTERN = Pattern.compile("^--include-(.+?) (.+?)\\s*#.+$");

  public static JextractSymbols parse(
    Path jextractIncludesPath,
    Path nativeHeaderPath,
    Predicate<String> typesExcluded,
    Predicate<String> namesExcluded) throws IOException {

    Map<Type, Collection<String>> includes = parseIncludes(jextractIncludesPath, typesExcluded, namesExcluded);
    Map<String, String> definitions = parseDefinitions(nativeHeaderPath, includes);

    return new JextractSymbols(includes, definitions);
  }

  private static Map<String, String> parseDefinitions(Path nativeHeaderPath, Map<Type, Collection<String>> includes) throws IOException {
    Map<String, String> definitions = new HashMap<>();
    String headerContents = Files.readString(nativeHeaderPath);
    List<String> tokens = Arrays.stream(headerContents.split(";"))
                                .map(String::trim)
                                .filter(s -> !StringUtils.isBlank(s))
                                .map(s -> s + ";")
                                .toList();

    includes.forEach((type, symbols) ->
      symbols.forEach(symbol ->
        tokens.stream()
              .filter(s -> s.contains(symbol))
              .findFirst()
              .ifPresent(s -> definitions.put(symbol, s.trim()))));

    return definitions;
  }

  private static Map<Type, Collection<String>> parseIncludes(Path jextractIncludesPath, Predicate<String> typesExcluded, Predicate<String> namesExcluded) throws IOException {
    Map<Type, Collection<String>> includes = new HashMap<>();

    try (Stream<String> lines = Files.lines(jextractIncludesPath)) {
      lines.map(INCLUDES_PATTERN::matcher)
           .filter(Matcher::matches)
           .map(matcher -> {
             try {
               String typeString = matcher.group(1);
               String symbolName = matcher.group(2);
               if (StringUtils.isBlank(typeString) || StringUtils.isBlank(symbolName)
                 || typesExcluded.test(typeString) || namesExcluded.test(symbolName))
                 return null;
               return Map.entry(Type.valueOf(typeString), symbolName.trim());
             } catch (Throwable _) {
               return null;
             }
           })
           .filter(Objects::nonNull)
           .forEach(entry ->
             includes.computeIfAbsent(entry.getKey(), _ -> new ArrayList<>())
                     .add(entry.getValue()));
    }
    return includes;
  }


}
