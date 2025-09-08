package io.github.rdlopes.tfhe.generator.parsers;

import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import static io.github.rdlopes.tfhe.generator.parsers.JextractIncludesFile.Type.constant;
import static io.github.rdlopes.tfhe.generator.parsers.JextractIncludesFile.from;
import static java.util.regex.Pattern.*;

public class SymbolsParser {
  private static final Logger logger = LoggerFactory.getLogger(SymbolsParser.class);

  public static SymbolsIndex parse(String headerClassName, Path nativeHeaderPath, Path jextractIncludesPath) throws ClassNotFoundException, IOException {
    FfmHeaderClass bindingsHeader = FfmHeaderClass.from(headerClassName);
    NativeHeaderFile nativeHeader = NativeHeaderFile.from(nativeHeaderPath);
    JextractIncludesFile jextractIncludes = from(jextractIncludesPath);

    SortedSet<String> names = new TreeSet<>();
    SortedMap<String, JextractIncludesFile.Type> types = new TreeMap<>();
    SortedMap<String, String> definitions = new TreeMap<>();

    bindingsHeader.filter(jextractIncludes::containsSymbol)
                  .forEach(symbol -> {
                    JextractIncludesFile.Type type = jextractIncludes.getType(symbol);
                    names.add(symbol);
                    types.put(symbol, type);
                    nativeHeader.lookup(symbol)
                                .ifPresent(storeDefinitionOf(symbol, type, definitions));
                  });

    return new SymbolsIndex(names, types, definitions);
  }

  private static Consumer<String> storeDefinitionOf(String symbol, JextractIncludesFile.Type type, SortedMap<String, String> definitions) {
    return original -> {
      String definition = original;
      if (type == constant && original.contains("enum")) {
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

}
