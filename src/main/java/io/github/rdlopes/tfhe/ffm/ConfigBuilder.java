package io.github.rdlopes.tfhe.ffm;


import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative_15.config_builder_build;
import static ai.zama.tfhe.TfheNative_15.config_builder_default;
import static ai.zama.tfhe.TfheNative_16.C_POINTER;

public class ConfigBuilder extends MemorySegmentWrapper {

  public ConfigBuilder(Arena arena) {
    super(arena, arena.allocate(C_POINTER));
    executeAndCheckError(() ->
      config_builder_default(pointer()));
  }

  public Config build() {
    Config config = new Config(arena());
    executeAndCheckError(() ->
      config_builder_build(value(), config.pointer()));
    return config;
  }
}
