package io.github.rdlopes.tfhe.generator;

import io.github.rdlopes.tfhe.generator.parsers.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.templates.TemplateContext;
import io.github.rdlopes.tfhe.generator.templates.TemplateWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.concurrent.Callable;

import static io.github.rdlopes.tfhe.generator.parsers.JextractIncludes.SymbolType.struct;
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
  @Option(names = {"--output-package"}, description = "package for the generated classes (optional)", defaultValue = "io.github.rdlopes.tfhe.api.types")
  String outputPackage;

  @SuppressWarnings("UnnecessaryModifier")
  public static void main(String[] args) {
    logger.info("Starting Generator");
    System.exit(new CommandLine(new Generator()).execute(args));
  }

  @Override
  public Integer call() throws Exception {
    logger.trace("call");

    SymbolsIndex symbolsIndex = SymbolsIndex.parse(nativeHeaderPath, jextractIncludesPath);
    logger.info("{} symbols parsed", symbolsIndex.symbolsByType()
                                                 .size());
    logger.debug("Symbols parsed: {}", symbolsIndex);

    TemplateWriter templateWriter = new TemplateWriter(templatesPrefix, outputPath, outputPackage);

    writeFheTypes(templateWriter, symbolsIndex, "FheBool");
    writeFheTypes(templateWriter, symbolsIndex, "FheInt");
    writeFheTypes(templateWriter, symbolsIndex, "FheUint");

    return 0;
  }

  private void writeFheTypes(TemplateWriter templateWriter, SymbolsIndex symbolsIndex, String typePrefix) throws IOException {
    logger.trace("writeFheTypes - templateWriter: {}, symbolsIndex: {}, typePrefix: {}", templateWriter, symbolsIndex, typePrefix);

    Collection<String> types = symbolsIndex.lookupSymbols(struct, s -> s.startsWith(typePrefix));

    for (String fheType : types) {
      TemplateContext templateContext = TemplateContext.forType(outputPackage, fheType, symbolsIndex);
      templateWriter.write("FheType", fheType, templateContext);

      TemplateContext compressedTemplateContext = TemplateContext.forCompressedType(outputPackage, fheType, symbolsIndex);
      templateWriter.write("CompressedFheType", "Compressed" + fheType, compressedTemplateContext);

      TemplateContext arrayTemplateContext = TemplateContext.forArrayType(outputPackage, fheType, symbolsIndex);
      if (arrayTemplateContext.hasArray()) {
        templateWriter.write("FheTypeArray", fheType + "Array", arrayTemplateContext);
      }
    }
  }

}
