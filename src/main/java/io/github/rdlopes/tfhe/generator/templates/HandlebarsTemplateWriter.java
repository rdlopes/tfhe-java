package io.github.rdlopes.tfhe.generator.templates;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import io.github.rdlopes.tfhe.generator.SymbolsIndex;
import io.github.rdlopes.tfhe.generator.templates.helpers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.*;

public class HandlebarsTemplateWriter {
  private final Handlebars handlebars;

  public HandlebarsTemplateWriter(Path templatesPath, SymbolsIndex symbolsIndex) {
    this.handlebars = new Handlebars(new FileTemplateLoader(templatesPath.toFile()));
    this.handlebars.registerHelpers(new ClassHelper(this.handlebars, symbolsIndex));
    this.handlebars.registerHelpers(new TestHelper(symbolsIndex));
    this.handlebars.registerHelpers(new ArithmeticsHelper(symbolsIndex));
    this.handlebars.registerHelpers(new ComparisonHelper(symbolsIndex));
    this.handlebars.registerHelpers(new BitwiseHelper(symbolsIndex));
  }

  public void write(SourceFileMapping sourceFileMapping) throws IOException {
    Template template = handlebars.compile(sourceFileMapping.templateName());
    Files.createDirectories(sourceFileMapping.filePath()
                                             .getParent());

    String contents = template.apply(sourceFileMapping.templateContext());
    Files.writeString(sourceFileMapping.filePath(), contents, CREATE, TRUNCATE_EXISTING, WRITE);
  }

}
