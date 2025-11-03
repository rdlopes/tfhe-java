package io.github.rdlopes.tfhe.generator;

import java.util.SortedSet;

public abstract class TemplateContext {
  private final String templateName;
  private final String packageName;
  private final String className;
  private final Symbols symbols;

  protected TemplateContext(String templateName, String packageName, String className, Symbols symbols) {
    this.templateName = templateName;
    this.packageName = packageName;
    this.className = className;
    this.symbols = symbols;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getClassName() {
    return className;
  }

  public Symbols getSymbols() {
    return symbols;
  }

  public String getTemplateName() {
    return templateName;
  }

  public SortedSet<String> getSymbolsUsed() {
    return symbols.used();
  }
}
