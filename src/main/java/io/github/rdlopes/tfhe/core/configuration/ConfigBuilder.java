package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.TfheConfigurationBuilder;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheConfigurationBuilder.allocate;
import static io.github.rdlopes.tfhe.ffm.TfheConfigurationBuilder.initialize;

public record ConfigBuilder(MemorySegment pointer) implements Cloneable {

  public ConfigBuilder() {
    this(allocate());
    initialize(pointer);
  }

  public ConfigBuilder useCustomParameters(ShortintPBSParameters parameters) {
    TfheConfigurationBuilder.useCustomParameters(pointer, parameters.pointer());
    return this;
  }

  public ConfigBuilder useDedicatedCompactPublicKeyParameters(ShortintCompactPublicKeyEncryptionParameters compactPublicKeyParameters) {
    TfheConfigurationBuilder.useDedicatedCompactPublicKeyParameters(pointer, compactPublicKeyParameters.pointer());
    return this;
  }

  public Config build() {
    Config config = new Config();
    TfheConfigurationBuilder.build(pointer, config.pointer());
    return config;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ConfigBuilder clone() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    TfheConfigurationBuilder.clone(pointer, configBuilder.pointer());
    return configBuilder;
  }
}
