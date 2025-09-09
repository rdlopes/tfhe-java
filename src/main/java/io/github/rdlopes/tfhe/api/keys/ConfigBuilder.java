package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativeValue;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class ConfigBuilder extends NativeValue implements Cloneable {

  public ConfigBuilder() {
    //super(TfheHeader::config_builder_destroy);
    super(null);
    execute(() -> config_builder_default(getAddress()));
  }

  public void useCustomParameters(ShortintPBSParameters parameters) {
    execute(() -> config_builder_use_custom_parameters(getAddress(), parameters.getAddress()));
  }

  public void enableCompression(CompressionParameters compressionParameters) {
    execute(() -> config_builder_enable_compression(getAddress(), compressionParameters.getAddress()));
  }

  public void useDedicatedCompactPublicKeyParameters(ShortintCompactPublicKeyEncryptionParameters compactPublicKeyParameters) {
    execute(() -> use_dedicated_compact_public_key_parameters(getAddress(), compactPublicKeyParameters.getAddress()));
  }

  public Config build() {
    Config config = new Config();
    execute(() -> config_builder_build(getValue(), config.getAddress()));
    return config;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ConfigBuilder clone() {
    ConfigBuilder configBuilder = new ConfigBuilder();
    execute(() -> config_builder_clone(getValue(), configBuilder.getAddress()));
    return configBuilder;
  }
}
