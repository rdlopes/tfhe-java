package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import static io.github.rdlopes.tfhe.ffm.TfheWrapper.*;

public class ConfigBuilder extends AddressLayoutPointer implements Cloneable {

  public ConfigBuilder() {
    super(address -> {
      //FIXME crashes VM
      //config_builder_destroy(address.get(C_POINTER, 0));
    });
    executeWithErrorHandling(() -> config_builder_default(getAddress()));
  }

  public void useCustomParameters(ShortintPBSParameters parameters) {
    executeWithErrorHandling(() -> config_builder_use_custom_parameters(getAddress(), parameters.getAddress()));
  }

  public void enableCompression(CompressionParameters compressionParameters) {
    executeWithErrorHandling(() -> config_builder_enable_compression(getAddress(), compressionParameters.getAddress()));
  }

  public void useDedicatedCompactPublicKeyParameters(ShortintCompactPublicKeyEncryptionParameters compactPublicKeyParameters) {
    executeWithErrorHandling(() -> use_dedicated_compact_public_key_parameters(getAddress(), compactPublicKeyParameters.getAddress()));
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
}
