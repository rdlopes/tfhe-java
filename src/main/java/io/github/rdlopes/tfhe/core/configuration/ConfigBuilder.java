package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.ConfigurationBuilderBindings;

import java.lang.foreign.MemorySegment;

public record ConfigBuilder(MemorySegment address) implements Cloneable {

  public ConfigBuilder() {
    this(ConfigurationBuilderBindings.allocate());
    ConfigurationBuilderBindings.initialize(address);
  }

  public ConfigBuilder useCustomParameters(ShortintPBSParameters parameters) {
    ConfigurationBuilderBindings.useCustomParameters(address, parameters.address());
    return this;
  }

  public ConfigBuilder useDedicatedCompactPublicKeyParameters(ShortintCompactPublicKeyEncryptionParameters compactPublicKeyParameters) {
    ConfigurationBuilderBindings.useDedicatedCompactPublicKeyParameters(address, compactPublicKeyParameters.address());
    return this;
  }

  public ConfigBuilder enableCompression(CompressionParameters compressionParameters) {
    ConfigurationBuilderBindings.enableCompression(address, compressionParameters.address());
    return this;
  }

  public Config build() {
    Config config = new Config();
    ConfigurationBuilderBindings.build(address, config.address());
    return config;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ConfigBuilder clone() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    ConfigurationBuilderBindings.clone(address, configBuilder.address());
    return configBuilder;
  }
}
