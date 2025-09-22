package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class ConfigBuilder extends NativePointer implements Cloneable {
  private static final Logger logger = LoggerFactory.getLogger(ConfigBuilder.class);

  ConfigBuilder() {
    logger.trace("init");

    super(null);
    execute(() -> config_builder_default(getAddress()));
  }

  public Config build() {
    logger.trace("build");

    Config config = new Config();
    execute(() -> config_builder_build(getValue(), config.getAddress()));

    return config;
  }

  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public ConfigBuilder clone() {
    logger.trace("clone");

    ConfigBuilder configBuilder = new ConfigBuilder();
    execute(() -> config_builder_clone(getValue(), configBuilder.getAddress()));

    return configBuilder;
  }
}
