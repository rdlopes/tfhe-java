package io.github.rdlopes.tfhe.api;

import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative_15.compressed_server_key_new;
import static ai.zama.tfhe.TfheNative_15.compressed_server_key_safe_serialize;
import static ai.zama.tfhe.TfheNative_16.C_POINTER;

public class CompressedServerKey extends FfmWrapper {

  public CompressedServerKey(Arena arena, ClientKey clientKey) {
    super(arena, arena.allocate(C_POINTER));
    executeAndCheckError(() ->
      compressed_server_key_new(clientKey.value(), pointer()));
  }

  public byte[] getBytes() {
    return copyBytesFrom(buffer ->
      compressed_server_key_safe_serialize(value(), buffer, Integer.MAX_VALUE));
  }
}
