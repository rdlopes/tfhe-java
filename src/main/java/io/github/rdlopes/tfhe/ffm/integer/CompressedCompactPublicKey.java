package io.github.rdlopes.tfhe.ffm.integer;

import io.github.rdlopes.tfhe.ffm.MemorySegmentWrapper;

import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative_16.*;

public class CompressedCompactPublicKey extends MemorySegmentWrapper {

  public CompressedCompactPublicKey(Arena arena) {
    super(arena, arena.allocate(C_POINTER));
  }

  public PublicKey decompress() {
    PublicKey publicKey = new PublicKey(arena());

    executeSafely(() ->
      compressed_compact_public_key_decompress(value(), publicKey.pointer()));

    return publicKey;
  }

  public byte[] getBytes() {
    return executeSafelyToDynamicBuffer((buffer) ->
      compressed_compact_public_key_safe_serialize(value(), buffer, Integer.MAX_VALUE));
  }
}
