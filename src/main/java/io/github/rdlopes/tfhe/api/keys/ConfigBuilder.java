package io.github.rdlopes.tfhe.api.keys;

import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

@SuppressWarnings({"java:S2975", "java:S1182"})
public class ConfigBuilder extends NativePointer implements Cloneable {
  private static final Logger logger = LoggerFactory.getLogger(ConfigBuilder.class);

  private final java.util.concurrent.atomic.AtomicBoolean consumed = new java.util.concurrent.atomic.AtomicBoolean(false);

  public ConfigBuilder() {
    logger.trace("init");

    super(TfheHeader::config_builder_destroy);
    execute(() -> config_builder_default(getAddress()));
  }

  public Config build() {
    logger.trace("build");

    if (!consumed.compareAndSet(false, true)) {
      throw new IllegalStateException("This builder has already been built/consumed.");
    }

    Config config = new Config();
    execute(() -> config_builder_build(getValue(), config.getAddress()));
    this.release();

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
