package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative.C_POINTER;
import static ai.zama.tfhe.TfheNative_16.client_key_safe_serialize;
import static ai.zama.tfhe.TfheNative_16.compressed_compact_public_key_new;

public class ClientKey extends MemorySegmentWrapper {

  public ClientKey(Arena arena) {
    super(arena, arena.allocate(C_POINTER));
  }

  public CompressedCompactPublicKey generateCompressedCompactPublicKey() {
    CompressedCompactPublicKey compressedCompactPublicKey = new CompressedCompactPublicKey(arena());

    executeAndCheckError(() ->
      compressed_compact_public_key_new(value(), compressedCompactPublicKey.pointer()));

    return compressedCompactPublicKey;
  }

  public byte[] getBytes() {
    return copyBytesFrom(buffer ->
      client_key_safe_serialize(value(), buffer, Integer.MAX_VALUE));
  }

}
