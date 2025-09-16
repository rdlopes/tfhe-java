package io.github.rdlopes.tfhe.generator.templates;

import java.nio.file.Path;

public record SourceFileMapping(String templateName, Path filePath, TemplateContext templateContext) {
}
