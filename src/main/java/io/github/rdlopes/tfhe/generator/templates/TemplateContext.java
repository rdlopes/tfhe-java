package io.github.rdlopes.tfhe.generator.templates;

import java.util.Collection;

public abstract class TemplateContext {
  private final String packageName;
  private final String className;

  public TemplateContext(String packageName, String className) {
    this.packageName = packageName;
    this.className = className;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getClassName() {
    return className;
  }

  public String getFullClassName() {
    return packageName + "." + className;
  }

  public abstract Collection<String> getImports();
}
