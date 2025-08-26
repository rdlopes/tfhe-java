package io.github.rdlopes.tfhe.core.configuration;

import io.github.rdlopes.tfhe.ffm.ConfigBindings;

import java.lang.foreign.MemorySegment;

public record Config(MemorySegment pointer) {
  public Config() {
    this(ConfigBindings.allocate());
  }
}
