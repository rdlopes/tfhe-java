package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.Arena;
import java.util.Map;

import static ai.zama.tfhe.TfheNative.C_POINTER;
import static ai.zama.tfhe.TfheNative_1.generate_keys;
import static ai.zama.tfhe.TfheNative_16.client_key_generate;

public final class Config extends MemorySegmentWrapper {

  public Config(Arena arena) {
    super(arena, arena.allocate(C_POINTER));
  }

  public ClientKey generateClientKey() {
    ClientKey clientKey = new ClientKey(arena());

    executeAndCheckError(() ->
      client_key_generate(value(), clientKey.pointer()));

    return clientKey;
  }

  public Map.Entry<ClientKey, ServerKey> generateKeys() {
    ClientKey clientKey = new ClientKey(arena());
    ServerKey serverKey = new ServerKey(arena());

    executeAndCheckError(() ->
      generate_keys(value(), clientKey.pointer(), serverKey.pointer()));

    return Map.entry(clientKey, serverKey);
  }
}
