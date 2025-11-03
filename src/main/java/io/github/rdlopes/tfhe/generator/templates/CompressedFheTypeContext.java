package io.github.rdlopes.tfhe.generator.templates;

import io.github.rdlopes.tfhe.generator.Symbols;
import io.github.rdlopes.tfhe.generator.TemplateContext;

public class CompressedFheTypeContext extends TemplateContext {
  private final String typeName;

  public static CompressedFheTypeContext forType(String basePackage, String typeName, Symbols symbols) {
    return new CompressedFheTypeContext(
      basePackage + ".types",
      typeName,
      "Compressed" + typeName,
      symbols.withFilter(s -> true));
  }

  protected CompressedFheTypeContext(String packageName, String typeName, String className, Symbols symbols) {
    super("CompressedFheType", packageName, className, symbols);
    this.typeName = typeName;
  }

  public String getTypeName() {
    return typeName;
  }

}
