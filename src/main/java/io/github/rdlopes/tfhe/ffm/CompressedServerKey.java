package io.github.rdlopes.tfhe.ffm;

import java.lang.foreign.Arena;

import static ai.zama.tfhe.TfheNative_15.*;
import static ai.zama.tfhe.TfheNative_16.C_POINTER;

public class CompressedServerKey extends MemorySegmentWrapper {

  public CompressedServerKey(Arena arena, ClientKey clientKey) {
    super(arena, arena.allocate(C_POINTER));
    executeAndCheckError(() ->
      compressed_server_key_new(clientKey.value(), pointer()));
  }

  public ServerKey decompress() {
    ServerKey serverKey = new ServerKey(arena());
    executeAndCheckError(() ->
      compressed_server_key_decompress(value(), serverKey.pointer()));

    return serverKey;
  }

  public byte[] getBytes() {
    return copyBytesFrom(buffer ->
      compressed_server_key_safe_serialize(value(), buffer, Integer.MAX_VALUE));
  }
}
