package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.ffm.ServerKeyBindings;

import java.lang.foreign.MemorySegment;

public record ServerKey(MemorySegment address) {
  public ServerKey() {
    this(ServerKeyBindings.allocate());
  }
}
