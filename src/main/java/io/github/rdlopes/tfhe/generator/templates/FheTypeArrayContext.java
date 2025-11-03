package io.github.rdlopes.tfhe.generator.templates;

import io.github.rdlopes.tfhe.generator.Symbols;
import io.github.rdlopes.tfhe.generator.TemplateContext;

public final class FheTypeArrayContext extends TemplateContext {
  private final String typeName;

  public FheTypeArrayContext(String packageName, String className, String typeName, Symbols symbols) {
    super("FheTypeArray", packageName, className, symbols);
    this.typeName = typeName;
  }

  public String getTypeName() {
    return typeName;
  }

}
