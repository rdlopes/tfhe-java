package io.github.rdlopes.tfhe.generator.templates;

import java.util.Collection;
import java.util.stream.Stream;

public abstract class SourceContext extends TemplateContext {
  private final Class<?> superClass;

  public SourceContext(String packageName, String className, Class<?> superClass) {
    super(packageName, className);
    this.superClass = superClass;
  }

  public Class<?> getSuperClass() {
    return superClass;
  }

  public abstract Collection<Class<?>> getInterfaces();

  @Override
  public Collection<String> getImports() {
    return Stream.concat(Stream.of(superClass), getInterfaces().stream())
                 .map(Class::getName)
                 .toList();
  }
}
