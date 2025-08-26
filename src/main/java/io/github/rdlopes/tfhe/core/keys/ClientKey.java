package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.ffm.ClientKeyBindings;

import java.lang.foreign.MemorySegment;

public record ClientKey(MemorySegment address) {
  public ClientKey() {
    this(ClientKeyBindings.allocate());
  }
}
