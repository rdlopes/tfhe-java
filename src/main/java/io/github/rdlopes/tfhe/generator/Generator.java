package io.github.rdlopes.tfhe.generator;

import io.github.rdlopes.tfhe.generator.parsers.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.templates.FheTypesContext;
import io.github.rdlopes.tfhe.generator.templates.TemplateContext;
import io.github.rdlopes.tfhe.generator.templates.TemplateWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

import static io.github.rdlopes.tfhe.generator.parsers.JextractIncludes.SymbolType.constant;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(name = "generator", description = "Generates Java source files from TFHE nativeType", mixinStandardHelpOptions = true)
public class Generator implements Callable<Integer> {
  private static final Logger logger = LoggerFactory.getLogger(Generator.class);

  @Option(names = {"--native-header"}, description = "path to tfhe.h (optional)", defaultValue = ".native/tfhe-rs/target/release/tfhe.h")
  Path nativeHeaderPath;
  @Option(names = {"--jextract-includes"}, description = "path to jextract includes file (optional)", defaultValue = ".native/tfhe-rs/jextract-includes-filtered.txt")
  Path jextractIncludesPath;
  @Option(names = {"--templates-prefix"}, description = "templates prefix, resolved from classpath (optional)", defaultValue = "/templates/")
  String templatesPrefix;
  @Option(names = {"--output-path"}, description = "directory where to generate Java sources (optional)", defaultValue = "src/main/java")
  Path outputPath;
  @Option(names = {"--base-package"}, description = "package for the generated classes (optional)", defaultValue = "io.github.rdlopes.tfhe.api.types")
  String basePackage;

  @SuppressWarnings("UnnecessaryModifier")
  public static void main(String[] args) {
    logger.info("Starting Generator");
    System.exit(new CommandLine(new Generator()).execute(args));
  }

  @Override
  public Integer call() throws Exception {
    logger.trace("call");

    SymbolsIndex globalIndex = SymbolsIndex.parse(nativeHeaderPath, jextractIncludesPath)
                                           .withFilter(symbol ->
                                             !symbol.startsWith("core_crypto")
                                               && !symbol.toLowerCase()
                                                         .startsWith("boolean")
                                               && !symbol.toLowerCase()
                                                         .startsWith("shortint")
                                           );
    logger.info("{} symbols parsed", globalIndex.symbols()
                                                .size());
    logger.debug("Symbols parsed: {}", globalIndex);

    TemplateWriter templateWriter = new TemplateWriter(templatesPrefix, outputPath, basePackage);

    Set<String> symbolsUsed = new HashSet<>();
    symbolsUsed.addAll(writeFheTypesEnum(templateWriter, globalIndex));
    symbolsUsed.addAll(writeFheTypes(templateWriter, globalIndex));

    symbolsUsed.addAll(globalIndex.used());

    TreeSet<String> symbolsUnused = new TreeSet<>(globalIndex.symbols());
    symbolsUnused.removeAll(symbolsUsed);
    logger.info("{}/{} symbols used", symbolsUsed.size(), globalIndex.symbols()
                                                                     .size());
    logger.info("{} Unused symbols", symbolsUnused.size());
    logger.info("Unused symbols: {}", symbolsUnused);

    return 0;
  }

  private Set<String> writeFheTypes(TemplateWriter templateWriter, SymbolsIndex globalIndex) throws IOException {
    logger.trace("writeFheTypes - templateWriter: {}, globalIndex: {}", templateWriter, globalIndex);

    Set<String> symbolsUsed = new HashSet<>();
    Collection<String> types = globalIndex.lookupSymbols(constant, s -> s.startsWith("Type_"))
                                          .stream()
                                          .map(s -> substringAfter(s, "Type_"))
                                          .toList();

    for (String type : types) {

      String fheType = globalIndex.lookupSymbol(type);
      if (fheType == null) continue;
      TemplateContext typeContext = TemplateContext.forType(basePackage, fheType, globalIndex);
      templateWriter.write("FheType", fheType, typeContext);
      symbolsUsed.addAll(typeContext.symbolsIndex()
                                    .used());

      String compressedFheType = globalIndex.lookupSymbol("Compressed" + type);
      if (compressedFheType == null) continue;
      TemplateContext compressedContext = TemplateContext.forCompressedType(basePackage, fheType, globalIndex);
      templateWriter.write("CompressedFheType", compressedFheType, compressedContext);
      symbolsUsed.addAll(compressedContext.symbolsIndex()
                                          .used());

      String arraySymbol = globalIndex.lookupSymbol(TemplateContext.nativeType(fheType + "Array"));
      if (arraySymbol == null) continue;
      TemplateContext arrayContext = TemplateContext.forArrayType(basePackage, fheType, globalIndex);
      if (arrayContext.hasArray()) {
        templateWriter.write("FheTypeArray", fheType + "Array", arrayContext);
        symbolsUsed.addAll(arrayContext.symbolsIndex()
                                       .used());
      }
    }

    return symbolsUsed;
  }

  private Set<String> writeFheTypesEnum(TemplateWriter templateWriter, SymbolsIndex globalIndex) throws IOException {
    logger.trace("writeFheTypesEnum - templateWriter: {}, globalIndex: {}", templateWriter, globalIndex);

    SymbolsIndex typesIndex = globalIndex.withFilter(s -> s.startsWith("Type_"));
    FheTypesContext fheTypesContext = new FheTypesContext(basePackage, typesIndex);
    templateWriter.write("FheTypes", "FheTypes", fheTypesContext);

    return typesIndex.used();
  }

}
