package io.github.rdlopes.tfhe.generator;

import io.github.rdlopes.tfhe.generator.templates.FheTypeContext;
import io.github.rdlopes.tfhe.generator.templates.FheTypesContext;
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
  @Option(names = {"--base-package"}, description = "package for the generated classes (optional)", defaultValue = "io.github.rdlopes.tfhe.api")
  String basePackage;

  @SuppressWarnings("UnnecessaryModifier")
  public static void main(String[] args) {
    logger.info("Starting Generator");
    System.exit(new CommandLine(new Generator()).execute(args));
  }

  @Override
  public Integer call() throws Exception {
    logger.trace("call");

    Symbols globalIndex = Symbols.parse(nativeHeaderPath, jextractIncludesPath)
                                 .withFilter(symbol ->
                                   !symbol.startsWith("core_crypto")
                                     && !symbol.toLowerCase()
                                               .startsWith("boolean")
                                     && !symbol.toLowerCase()
                                               .startsWith("shortint")
                                 );
    logger.info("{} symbols parsed", globalIndex.set()
                                                .size());
    logger.debug("Symbols parsed: {}", globalIndex);

    TemplateWriter templateWriter = new TemplateWriter(templatesPrefix, outputPath);

    Set<String> symbolsUsed = new HashSet<>();

    FheTypesContext fheTypesContext = FheTypesContext.forTypes(basePackage, globalIndex);
    templateWriter.write(fheTypesContext);
    symbolsUsed.addAll(templateWriter.write(fheTypesContext));

    symbolsUsed.addAll(writeFheTypes(templateWriter, globalIndex));
    symbolsUsed.addAll(globalIndex.used());

    TreeSet<String> symbolsUnused = new TreeSet<>(globalIndex.set());
    symbolsUnused.removeAll(symbolsUsed);
    logger.info("{}/{} symbols used", symbolsUsed.size(), globalIndex.set()
                                                                     .size());
    logger.info("{} Unused symbols", symbolsUnused.size());
    logger.info("Unused symbols: {}", symbolsUnused);

    return 0;
  }

  private Set<String> writeFheTypes(TemplateWriter templateWriter, Symbols globalSymbols) throws IOException {
    logger.trace("writeFheTypes - templateWriter: {}, globalSymbols: {}", templateWriter, globalSymbols);

    Set<String> symbolsUsed = new HashSet<>();
    Collection<String> types = globalSymbols.lookupSymbols(s -> s.startsWith("Type_"))
                                            .stream()
                                            .map(s -> substringAfter(s, "Type_"))
                                            .toList();

    for (String type : types) {

      String fheType = globalSymbols.lookupSymbol(type);
      if (fheType == null) continue;
      FheTypeContext typeContext = FheTypeContext.forType(basePackage + ".types", fheType, globalSymbols);
      templateWriter.write(typeContext);
      symbolsUsed.addAll(typeContext.getSymbols()
                                    .used());

      String compressedFheType = globalSymbols.lookupSymbol("Compressed" + type);
      if (compressedFheType == null) continue;
      FheTypeContext compressedContext = FheTypeContext.forCompressedType(basePackage + ".types", fheType, globalSymbols);
      templateWriter.write(compressedContext);
      symbolsUsed.addAll(compressedContext.getSymbols()
                                          .used());

      String arraySymbol = globalSymbols.lookupSymbol(FheTypeContext.nativeType(fheType + "Array"));
      if (arraySymbol == null) continue;
      FheTypeContext arrayContext = FheTypeContext.forArrayType(basePackage + ".types", fheType, globalSymbols);
      if (arrayContext.hasArray()) {
        templateWriter.write(arrayContext);
        symbolsUsed.addAll(arrayContext.getSymbols()
                                       .used());
      }
    }

    return symbolsUsed;
  }

}
