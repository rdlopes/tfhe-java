package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.ConfigurationBuilderBindings;

import java.lang.foreign.MemorySegment;

public record ConfigBuilder(MemorySegment pointer) implements Cloneable {

  public ConfigBuilder() {
    this(ConfigurationBuilderBindings.allocate());
    ConfigurationBuilderBindings.initialize(pointer);
  }

  public ConfigBuilder useCustomParameters(ShortintPBSParameters parameters) {
    ConfigurationBuilderBindings.useCustomParameters(pointer, parameters.pointer());
    return this;
  }

  public ConfigBuilder useDedicatedCompactPublicKeyParameters(ShortintCompactPublicKeyEncryptionParameters compactPublicKeyParameters) {
    ConfigurationBuilderBindings.useDedicatedCompactPublicKeyParameters(pointer, compactPublicKeyParameters.pointer());
    return this;
  }

  public ConfigBuilder enableCompression(CompressionParameters compressionParameters) {
    ConfigurationBuilderBindings.enableCompression(pointer, compressionParameters.pointer());
    return this;
  }

  public Config build() {
    Config config = new Config();
    ConfigurationBuilderBindings.build(pointer, config.pointer());
    return config;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ConfigBuilder clone() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    ConfigurationBuilderBindings.clone(pointer, configBuilder.pointer());
    return configBuilder;
  }
}
