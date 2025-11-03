package io.github.rdlopes.tfhe.generator;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import io.github.rdlopes.tfhe.generator.templates.TemplateHelper;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.SortedSet;

import static com.github.jknack.handlebars.EscapingStrategy.NOOP;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public record TemplateWriter(Handlebars handlebars, Path outputPath) {

  public TemplateWriter(String templatesPrefix, Path outputPath) {
    Handlebars handlebars = new Handlebars()
      .with(new ClassPathTemplateLoader(templatesPrefix))
      .registerHelpers(TemplateHelper.class)
      .with(NOOP)
      .prettyPrint(true)
      .preEvaluatePartialBlocks(true);
    this(handlebars, outputPath);
    StringHelpers.register(handlebars);
  }

  private Path resolvePath(TemplateContext templateContext) {
    String packagePath = templateContext.getPackageName()
                                        .replace('.', File.separatorChar);
    return outputPath.resolve(packagePath)
                     .resolve(templateContext.getClassName() + ".java");
  }

  public SortedSet<String> write(TemplateContext templateContext) throws IOException {
    Path filePath = resolvePath(templateContext);
    Template template = handlebars().compile(templateContext.getTemplateName());
    Files.createDirectories(filePath.getParent());

    try (Writer writer = Files.newBufferedWriter(filePath, UTF_8, CREATE, TRUNCATE_EXISTING)) {
      template.apply(templateContext, writer);
      return templateContext.getSymbols()
                            .used();
    }
  }

}
