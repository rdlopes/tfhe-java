package io.github.rdlopes.tfhe.generator.mappings;

import io.github.rdlopes.tfhe.generator.templates.TestContext;

public class FheTypeTestContext<T extends FheTypeContext> extends TestContext<T> {
  protected FheTypeTestContext(String packageName, String className, T tested) {
    super(packageName, className, tested);
  }

}
