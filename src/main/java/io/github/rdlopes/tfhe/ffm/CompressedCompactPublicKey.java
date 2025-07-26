package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative_16.*;

public class CompressedCompactPublicKey extends MemorySegmentWrapper {

  public CompressedCompactPublicKey(Arena arena) {
    super(arena, arena.allocate(C_POINTER));
  }

  public PublicKey decompress() {
    PublicKey publicKey = new PublicKey(arena());

    executeAndCheckError(() ->
      compressed_compact_public_key_decompress(value(), publicKey.pointer()));

    return publicKey;
  }

  public byte[] getBytes() {
    return copyBytesFrom((buffer) ->
      compressed_compact_public_key_safe_serialize(value(), buffer, Integer.MAX_VALUE));
  }
}
