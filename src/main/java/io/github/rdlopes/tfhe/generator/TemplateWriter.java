package io.github.rdlopes.tfhe.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import io.github.rdlopes.tfhe.generator.mappers.ClassMapping;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static freemarker.template.Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS;
import static freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.*;

public class TemplateWriter {
  private final Configuration freemarkerConfig;

  public TemplateWriter(Path templatePath, SymbolsIndex symbolsIndex) throws TemplateModelException, IOException {
    this.freemarkerConfig = createFreemarkerConfiguration(templatePath, symbolsIndex);
  }

  private Configuration createFreemarkerConfiguration(Path templatesDirectory, SymbolsIndex symbolsIndex) throws IOException, TemplateModelException {
    Configuration config = new Configuration(DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    config.setDirectoryForTemplateLoading(templatesDirectory.toFile());
    config.setTemplateExceptionHandler(RETHROW_HANDLER);
    config.setDefaultEncoding(UTF_8.name());
    config.setBooleanFormat("c");
    config.setSharedVariable("symbolsIndex", symbolsIndex);

    return config;
  }

  public void write(ClassMapping classMapping) throws IOException, TemplateException {
    Template template = freemarkerConfig.getTemplate(classMapping.templateName());
    Files.createDirectories(classMapping.outputDirectory());

    try (Writer writer = Files.newBufferedWriter(classMapping.outputFilePath(), CREATE, TRUNCATE_EXISTING, WRITE)) {
      template.process(classMapping.data(), writer);
    }
  }

}
