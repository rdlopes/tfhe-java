package io.github.rdlopes.tfhe.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardOpenOption.*;

public class Templates {

  private static final Map<Class<? extends FheType>, Template> TEMPLATE_CACHE = new HashMap<>();
  private static Configuration freemarkerConfig;

  private static Configuration getFreemarkerConfiguration(Path templatesDirectory) {
    if (freemarkerConfig == null) {
      freemarkerConfig = new Configuration(Configuration.VERSION_2_3_33);
      try {
        freemarkerConfig.setDirectoryForTemplateLoading(templatesDirectory.toFile());
        freemarkerConfig.setDefaultEncoding("UTF-8");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return freemarkerConfig;
  }

  private static <T extends FheType> Template getTemplate(T fheType, Path templatesDirectory) {
    return TEMPLATE_CACHE.computeIfAbsent(fheType.getClass(), clazz -> {
      String templateName = "%s.ftl".formatted(clazz.getSimpleName());
      try {
        Configuration config = getFreemarkerConfiguration(templatesDirectory);
        return config.getTemplate(templateName);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  public static void generateTypes(
    Collection<FheType> fheTypes,
    Path outputDirectory,
    Path templatesDirectory) throws IOException {
    generateTypes(fheTypes, outputDirectory, null, templatesDirectory, null);
  }

  public static void generateTypes(
    Collection<FheType> fheTypes,
    Path outputDirectory,
    Path testOutputDirectory,
    Path templatesDirectory,
    String targetTestPackage) throws IOException {

    try {
      FileUtils.deleteDirectory(outputDirectory.toFile());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    if (testOutputDirectory != null) {
      try {
        FileUtils.deleteDirectory(testOutputDirectory.toFile());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    for (FheType fheType : fheTypes) {
      if (fheType.exists()) {
        generateMainClass(fheType, fheTypes, outputDirectory, templatesDirectory);

        if (testOutputDirectory != null) {
          generateTestClass(fheType, fheTypes, testOutputDirectory, templatesDirectory, targetTestPackage);
        }
      }
    }
  }

  private static void generateMainClass(FheType fheType, Collection<FheType> fheTypes, Path outputDirectory, Path templatesDirectory) throws IOException {
    if (shouldSkipGeneration(fheType)) {
      return;
    }

    Path outputFilePath = outputDirectory.resolve(fheType.packageName()
                                                         .replace(".", "/"))
                                         .resolve("%s.java".formatted(fheType.name()));
    Files.createDirectories(outputFilePath.getParent());
    Template template = getTemplate(fheType, templatesDirectory);
    try (Writer fheTypeWriter = Files.newBufferedWriter(outputFilePath, CREATE, TRUNCATE_EXISTING, WRITE)) {
      Map<String, Object> dataModel = Map.of(
        "fheType", fheType,
        "allFheTypes", fheTypes
      );
      template.process(dataModel, fheTypeWriter);
    } catch (IOException | TemplateException e) {
      throw new RuntimeException(e);
    }
  }

  private static boolean shouldSkipGeneration(FheType fheType) {
    String className = fheType.name();
    return className.matches("^[UI]\\d+$");
  }

  private static void generateTestClass(FheType fheType, Collection<FheType> fheTypes, Path testOutputDirectory, Path templatesDirectory, String targetTestPackage) {
    if (shouldSkipGeneration(fheType)) {
      return;
    }

    String testTemplateFileName = "%sTest.ftl".formatted(fheType.getClass()
                                                                .getSimpleName());
    try {
      Configuration config = getFreemarkerConfiguration(templatesDirectory);
      Template testTemplate = config.getTemplate(testTemplateFileName);

      String testPackage = targetTestPackage != null ? targetTestPackage : fheType.packageName()
                                                                                  .replace("core.types", "test.core.types");
      Path testOutputFilePath = testOutputDirectory.resolve(testPackage.replace(".", "/"))
                                                   .resolve("%sTest.java".formatted(fheType.name()));
      Files.createDirectories(testOutputFilePath.getParent());

      try (Writer testWriter = Files.newBufferedWriter(testOutputFilePath, CREATE, TRUNCATE_EXISTING, WRITE)) {
        Map<String, Object> dataModel = Map.of(
          "fheType", fheType,
          "allFheTypes", fheTypes
        );
        testTemplate.process(dataModel, testWriter);
      }
    } catch (IOException | TemplateException e) {
      System.err.println("Warning: Could not generate test for " + fheType.name() + ": " + e.getMessage());
    }
  }
}
