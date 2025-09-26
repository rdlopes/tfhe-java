package io.github.rdlopes.tfhe.generator.templates;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.jknack.handlebars.EscapingStrategy.NOOP;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public record TemplateWriter(Handlebars handlebars, Path outputPath, String outputPackage) {

  public TemplateWriter(String templatesPrefix, Path outputPath, String outputPackage) {
    Handlebars handlebars = new Handlebars()
      .with(new ClassPathTemplateLoader(templatesPrefix))
      .registerHelpers(TemplateHelper.class)
      .with(NOOP)
      .prettyPrint(true)
      .preEvaluatePartialBlocks(true);
    this(handlebars, outputPath, outputPackage);
    StringHelpers.register(handlebars);
  }

  private Path resolvePath(Path basePath, String className) {
    return basePath.resolve(outputPackage.replace('.', File.separatorChar))
                   .resolve(className + ".java");
  }

  public void write(String templateName, String className, TemplateContext templateContext) throws IOException {
    Path filePath = resolvePath(outputPath, className);
    Template template = handlebars().compile(templateName);
    Files.createDirectories(filePath.getParent());

    templateContext.symbolsIndex()
                   .used()
                   .add(className);

    try (Writer writer = Files.newBufferedWriter(filePath, UTF_8, CREATE, TRUNCATE_EXISTING)) {
      template.apply(templateContext, writer);
    }
  }

}
