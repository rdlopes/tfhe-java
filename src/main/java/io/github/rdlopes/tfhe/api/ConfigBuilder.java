package io.github.rdlopes.tfhe.api;


import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative_15.config_builder_default;
import static ai.zama.tfhe.TfheNative_16.C_POINTER;

public class ConfigBuilder extends FfmWrapper {

  public ConfigBuilder(Arena arena) {
    super(arena, arena.allocate(C_POINTER));
    executeAndCheckError(() ->
      config_builder_default(pointer()));
  }

  public Config build() {
    return new Config(arena(), this);
  }
}
