package io.github.rdlopes.tfhe.generator.mappings;

import io.github.rdlopes.tfhe.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CompressedFheTypeContext<T extends FheTypeContext> extends FheTypeContext {
  private final T compressed;

  public CompressedFheTypeContext(String packageName, String className, Class<?> superClass, T compressed) {
    super(packageName, className, superClass, "compressed_" + compressed.getNativePrefix(), compressed.getBitSize());
    this.compressed = compressed;
  }

  @Override
  public Collection<Class<?>> getInterfaces() {
    return new ArrayList<>(List.of(
      FheEncryption.class,
      FheDecompression.class,
      FheSerialization.class,
      FheSafeSerialization.class,
      FheCloneable.class
    ));
  }

  @Override
  public Collection<String> getImports() {
    Collection<String> imports = new ArrayList<>(super.getImports());
    imports.add(compressed.getFullClassName());
    imports.addAll(compressed.getImports());

    return imports;
  }

  public T getCompressed() {
    return compressed;
  }
}
