package io.github.rdlopes.tfhe.api;

import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative.C_POINTER;
import static ai.zama.tfhe.TfheNative_16.client_key_generate;
import static ai.zama.tfhe.TfheNative_16.client_key_safe_serialize;

public class ClientKey extends FfmWrapper {

  public ClientKey(Arena arena, Config config) {
    super(arena, arena.allocate(C_POINTER));
    executeAndCheckError(() ->
      client_key_generate(config.value(), pointer()));
  }

  public byte[] getBytes() {
    return copyBytesFrom(buffer ->
      client_key_safe_serialize(value(), buffer, Integer.MAX_VALUE));
  }

}
