package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class ConfigBuilder extends AddressLayoutPointer implements Cloneable {

  public ConfigBuilder() {
    super();
    executeWithErrorHandling(() -> config_builder_default(getAddress()));
  }

  public ConfigBuilder useCustomParameters(ShortintPBSParameters parameters) {
    executeWithErrorHandling(() -> config_builder_use_custom_parameters(getAddress(), parameters.getAddress()));
    return this;
  }

  public ConfigBuilder useDedicatedCompactPublicKeyParameters(ShortintCompactPublicKeyEncryptionParameters compactPublicKeyParameters) {
    executeWithErrorHandling(() -> use_dedicated_compact_public_key_parameters(getAddress(), compactPublicKeyParameters.getAddress()));
    return this;
  }

  public ConfigBuilder enableCompression(CompressionParameters compressionParameters) {
    executeWithErrorHandling(() -> config_builder_enable_compression(getAddress(), compressionParameters.getAddress()));
    return this;
  }

  public Config build() {
    Config config = new Config();
    executeWithErrorHandling(() -> config_builder_build(getValue(), config.getAddress()));
    return config;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ConfigBuilder clone() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    executeWithErrorHandling(() -> config_builder_clone(getValue(), configBuilder.getAddress()));
    return configBuilder;
  }

  public void destroy() {
    executeWithErrorHandling(() -> config_builder_destroy(getValue()));
  }
}
