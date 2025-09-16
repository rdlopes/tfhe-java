package io.github.rdlopes.tfhe.generator;

import io.github.rdlopes.tfhe.generator.mappings.FheMapper;
import io.github.rdlopes.tfhe.generator.parsers.FfmBindings;
import io.github.rdlopes.tfhe.generator.parsers.JextractIncludesFile;
import io.github.rdlopes.tfhe.generator.parsers.NativeHeaderFile;
import io.github.rdlopes.tfhe.generator.parsers.SymbolsParser;
import io.github.rdlopes.tfhe.generator.templates.HandlebarsTemplateWriter;
import io.github.rdlopes.tfhe.generator.templates.SourceFileMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.Callable;

import static io.github.rdlopes.tfhe.generator.parsers.JextractIncludesFile.from;
import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(name = "generator", description = "Generates Java source files from TFHE nativeType", mixinStandardHelpOptions = true)
public class Generator implements Callable<Integer> {
  private static final Logger logger = LoggerFactory.getLogger(Generator.class);

  @Option(names = {"--header-class"}, description = "header class name (optional)", defaultValue = "io.github.rdlopes.tfhe.ffm.TfheHeader")
  String headerClassName;
  @Option(names = {"--native-header"}, description = "path to tfhe.h (optional)", defaultValue = ".native/tfhe-rs/target/release/tfhe.h")
  Path nativeHeaderPath;
  @Option(names = {"--jextract-includes"}, description = "path to jextract includes file (optional)", defaultValue = ".native/tfhe-rs/jextract-includes-filtered.txt")
  Path jextractIncludesPath;
  @Option(names = {"--templates"}, description = "directory where are stored the templates (optional)", defaultValue = "src/main/resources/templates")
  Path templatesPath;
  @Option(names = {"--source-output"}, description = "directory where to generate Java sources (optional)", defaultValue = "target/generated-sources/generator")
  Path sourcesPath;
  @Option(names = {"--test-output"}, description = "directory where to generate Java test sources (optional)", defaultValue = "target/generated-test-sources/generator")
  Path testSourcesPath;
  @Option(names = {"--source-package"}, description = "base package for generated sources (optional)", defaultValue = "io.github.rdlopes.tfhe.api")
  String baseSourcePackage;
  @Option(names = {"--test-package"}, description = "base package for generated sources (optional)", defaultValue = "io.github.rdlopes.tfhe.api")
  String baseTestPackage;
  @Option(names = {"--report-unused"}, description = "logs used symbols (optional)", defaultValue = "true")
  Boolean reportUnusedSymbols;

  @SuppressWarnings("UnnecessaryModifier")
  public static void main(String[] args) {
    logger.info("Starting Generator");
    System.exit(new CommandLine(new Generator()).execute(args));
  }

  @Override
  public Integer call() throws Exception {
    logger.trace("call");

    FfmBindings bindings = FfmBindings.from(headerClassName);
    NativeHeaderFile nativeHeader = NativeHeaderFile.from(nativeHeaderPath);
    JextractIncludesFile jextractIncludes = from(jextractIncludesPath);
    SymbolsParser symbolsParser = new SymbolsParser(bindings, nativeHeader, jextractIncludes);
    SymbolsIndex symbolsIndex = symbolsParser.parse()
                                             .filtered(symbol ->
                                               symbol.startsWith("Type_")
                                                 || symbol.startsWith("fhe_")
                                                 || symbol.startsWith("compressed_fhe_"));
    logger.trace("Symbols parsed: {}", symbolsIndex);

    FheMapper fheMapper = new FheMapper(sourcesPath, testSourcesPath, baseSourcePackage, baseTestPackage);
    Collection<SourceFileMapping> sourceFileMappings = fheMapper.mapTypes(symbolsIndex);
    logger.trace("Sources mapped: {}", sourceFileMappings);

    HandlebarsTemplateWriter templateWriter = new HandlebarsTemplateWriter(templatesPath, symbolsIndex);
    for (SourceFileMapping sourceFileMapping : sourceFileMappings) {
      logger.debug("Writing source mapping: {}", sourceFileMapping);
      templateWriter.write(sourceFileMapping);
    }

    if (reportUnusedSymbols) {
      symbolsIndex.getUnusedSymbols()
                  .forEach(symbol -> logger.info("Unused symbol: {}", symbol));
    }

    return 0;
  }

}
