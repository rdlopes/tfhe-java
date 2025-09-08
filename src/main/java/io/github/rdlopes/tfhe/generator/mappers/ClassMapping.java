package io.github.rdlopes.tfhe.generator.mappers;

import java.nio.file.Path;
import java.util.Map;

public record ClassMapping(String templateName,
                           Path baseDirectory,
                           String packageName,
                           String outputFileName,
                           Map<String, Object> data) {

  public Path outputDirectory() {
    return baseDirectory.resolve(packageName.replace(".", "/"));
  }

  public Path outputFilePath() {
    return outputDirectory().resolve(outputFileName);
  }
}
