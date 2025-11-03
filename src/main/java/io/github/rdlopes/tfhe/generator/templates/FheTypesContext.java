package io.github.rdlopes.tfhe.generator.templates;

import io.github.rdlopes.tfhe.generator.Symbols;
import io.github.rdlopes.tfhe.generator.TemplateContext;

public final class FheTypesContext extends TemplateContext {

  public static FheTypesContext forTypes(String basePackage, Symbols symbols) {
    return new FheTypesContext(
      basePackage + ".types",
      "FheTypes",
      symbols.withFilter(s -> s.startsWith("Type_")));
  }

  public FheTypesContext(String packageName, String className, Symbols symbols) {
    super("FheTypes", packageName, className, symbols);
  }

}
