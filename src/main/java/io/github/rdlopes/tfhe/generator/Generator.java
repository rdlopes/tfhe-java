package io.github.rdlopes.tfhe.generator;

import io.github.rdlopes.tfhe.generator.mappers.ClassMapping;
import io.github.rdlopes.tfhe.generator.mappers.FheTypesMapper;
import io.github.rdlopes.tfhe.generator.parsers.SymbolsParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.Callable;

import static picocli.CommandLine.Command;
import static picocli.CommandLine.Option;

@Command(name = "generator", description = "Generates Java source files from TFHE types", mixinStandardHelpOptions = true)
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
  @Option(names = {"--source-output"}, description = "directory where to generate Java sources (optional)", defaultValue = "src/main/java")
  Path sourcesPath;
  @Option(names = {"--test-output"}, description = "directory where to generate Java test sources (optional)", defaultValue = "src/test/java")
  Path testSourcesPath;
  @Option(names = {"--source-package"}, description = "base package for generated sources (optional)", defaultValue = "io.github.rdlopes.tfhe.api.types")
  String baseSourcePackage;
  @Option(names = {"--test-package"}, description = "base package for generated sources (optional)", defaultValue = "io.github.rdlopes.tfhe.test.api.types")
  String baseTestPackage;

  static void main(String[] args) {
    logger.info("Starting Generator");
    System.exit(new CommandLine(new Generator()).execute(args));
  }

  @Override
  public Integer call() throws Exception {
    logger.trace("call");

    SymbolsIndex symbolsIndex = SymbolsParser.parse(headerClassName, nativeHeaderPath, jextractIncludesPath);
    logger.trace("Symbols parsed: {}", symbolsIndex.prettyPrint());

    FheTypesMapper fheTypesMapper = new FheTypesMapper(sourcesPath, testSourcesPath, baseSourcePackage, baseTestPackage);
    Collection<ClassMapping> classMappings = fheTypesMapper.generateMappings(symbolsIndex);
    logger.debug("Sources mapped: {}", classMappings);

    TemplateWriter templateWriter = new TemplateWriter(templatesPath, symbolsIndex);
    for (ClassMapping classMapping : classMappings) {
      logger.debug("Writing source mapping: {}", classMapping);
      templateWriter.write(classMapping);
    }

    return 0;
  }

}
