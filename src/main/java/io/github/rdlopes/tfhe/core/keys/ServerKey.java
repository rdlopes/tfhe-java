package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.ServerKeyBindings;

import java.lang.foreign.MemorySegment;

public record ServerKey(MemorySegment address) {
  public ServerKey() {
    this(ServerKeyBindings.allocate());
  }

  public static ServerKey deserialize(DynamicBufferView bufferView) {
    ServerKey serverKey = new ServerKey();
    ServerKeyBindings.deserialize(bufferView.address(), serverKey.address());
    return serverKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer buffer = new DynamicBuffer();
    ServerKeyBindings.serialize(address, buffer.address());
    return buffer;
  }

  public DynamicBuffer safeSerialize() {
    DynamicBuffer buffer = new DynamicBuffer();
    ServerKeyBindings.safeSerialize(address, buffer.address());
    return buffer;
  }
}
