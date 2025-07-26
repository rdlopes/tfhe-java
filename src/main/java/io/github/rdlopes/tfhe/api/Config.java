package io.github.rdlopes.tfhe.api;

import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative.C_POINTER;
import static ai.zama.tfhe.TfheNative.config_builder_build;

public final class Config extends FfmWrapper {

  public Config(Arena arena, ConfigBuilder configBuilder) {
    super(arena, arena.allocate(C_POINTER));
    executeAndCheckError(() ->
      config_builder_build(configBuilder.value(), pointer()));
  }
}
