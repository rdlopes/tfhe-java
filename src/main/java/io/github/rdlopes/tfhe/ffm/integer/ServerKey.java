package io.github.rdlopes.tfhe.ffm.integer;

import io.github.rdlopes.tfhe.ffm.MemorySegmentWrapper;

import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative_16.C_POINTER;

public class ServerKey extends MemorySegmentWrapper {

  public ServerKey(Arena arena) {
    super(arena, arena.allocate(C_POINTER));
  }
}
