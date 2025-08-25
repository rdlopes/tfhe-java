package io.github.rdlopes.tfhe.core.configuration;

import java.lang.foreign.MemorySegment;

import static io.github.rdlopes.tfhe.ffm.TfheMemoryAllocator.allocateConfig;

public record Config(MemorySegment pointer) {
  public Config() {
    this(allocateConfig());
  }
}
