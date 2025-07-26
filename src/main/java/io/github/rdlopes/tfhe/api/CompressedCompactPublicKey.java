package io.github.rdlopes.tfhe.api;

import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative_16.*;

public class CompressedCompactPublicKey extends FfmWrapper {

  public CompressedCompactPublicKey(Arena arena, ClientKey clientKey) {
    super(arena, arena.allocate(C_POINTER));
    executeAndCheckError(() ->
      compressed_compact_public_key_new(clientKey.value(), pointer()));
  }

  public byte[] getBytes() {
    return copyBytesFrom((buffer) ->
      compressed_compact_public_key_safe_serialize(value(), buffer, Integer.MAX_VALUE));
  }
}
