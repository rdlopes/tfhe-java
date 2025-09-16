package io.github.rdlopes.tfhe.generator.templates;

import java.util.Collection;
import java.util.stream.Stream;

public abstract class TestContext<T extends SourceContext> extends TemplateContext {
  private final T tested;

  public TestContext(String packageName, String className, T tested) {
    super(packageName, className);
    this.tested = tested;
  }

  public T getTested() {
    return tested;
  }

  @Override
  public Collection<String> getImports() {
    return Stream.concat(
                   Stream.of(tested.getFullClassName()),
                   tested.getImports()
                         .stream())
                 .toList();
  }
}
