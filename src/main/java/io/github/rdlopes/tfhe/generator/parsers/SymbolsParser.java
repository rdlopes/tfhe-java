package io.github.rdlopes.tfhe.generator.parsers;

import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class SymbolsParser {
  private static final Logger logger = LoggerFactory.getLogger(SymbolsParser.class);
  private final FfmBindings bindings;
  private final NativeHeaderFile nativeHeader;
  private final JextractIncludesFile jextractIncludes;

  public SymbolsParser(FfmBindings bindings, NativeHeaderFile nativeHeader, JextractIncludesFile jextractIncludes) {
    this.bindings = bindings;
    this.nativeHeader = nativeHeader;
    this.jextractIncludes = jextractIncludes;
  }

  private static Consumer<String> storeDefinitionOf(String symbol, SortedMap<String, String> definitions) {
    return original -> {
      String definition = original;
      if (original.contains("enum")) {
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
      definitions.put(symbol, definition);
    };
  }

  public SymbolsIndex parse() throws ClassNotFoundException, IOException {

    SortedSet<String> names = new TreeSet<>();
    SortedMap<String, String> definitions = new TreeMap<>();

    bindings.filter(s -> jextractIncludes.includes()
                                         .contains(s))
            .forEach(symbol -> {
              names.add(symbol);
              nativeHeader.lookup(symbol)
                          .ifPresent(storeDefinitionOf(symbol, definitions));
            });

    return new SymbolsIndex(names, definitions);
  }

}
