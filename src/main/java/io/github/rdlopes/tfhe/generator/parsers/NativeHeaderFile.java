package io.github.rdlopes.tfhe.generator.parsers;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public record NativeHeaderFile(Collection<String> definitions) {
  public static NativeHeaderFile from(Path path) throws IOException {
    String content = Files.readString(path);
    List<String> definitions = Arrays.stream(content.split(";"))
                                     .map(s -> s.replaceAll("\\s*//.*\\n", "\n"))
                                     .map(s -> s.replaceAll("\\s*#.*\\n", "\n"))
                                     .map(s -> s.replaceAll("extern \"C\".*\\{", ""))
                                     .map(String::trim)
                                     .map(s -> s.replaceAll("([^(].*) +(.*[^)])", "$1 $2"))
                                     .filter(s -> !StringUtils.isBlank(s))
                                     .map(s -> s + ";")
                                     .toList();
    return new NativeHeaderFile(definitions);
  }

  public String lookup(String symbol) {
    Function<String, Optional<String>> search =
      (match) -> definitions.stream()
                            .filter(s -> s.contains(match))
                            .findFirst();

    return search.apply(" " + symbol + " ")
                 .or(() -> search.apply(" " + symbol))
                 .or(() -> search.apply(symbol))
                 .orElse("");
  }
}
