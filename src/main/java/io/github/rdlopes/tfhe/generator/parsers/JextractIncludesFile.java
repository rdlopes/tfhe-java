package io.github.rdlopes.tfhe.generator.parsers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public record JextractIncludesFile(SortedSet<String> includes) {
  private static final Pattern INCLUDES_PATTERN = Pattern.compile("^--include-.+? (.+?)\\s*#.+$");

  public static JextractIncludesFile from(Path path) throws IOException {
    TreeSet<String> includes = new TreeSet<>();
    try (Stream<String> lines = Files.lines(path)) {
      lines.map(INCLUDES_PATTERN::matcher)
           .filter(Matcher::matches)
           .map(m -> m.group(1)
                      .trim())
           .filter(s ->
             !s.toLowerCase()
               .startsWith("shortint_")
               && !s.toLowerCase()
                    .startsWith("boolean_"))
           .forEach(includes::add);
    }
    return new JextractIncludesFile(includes);
  }

}
