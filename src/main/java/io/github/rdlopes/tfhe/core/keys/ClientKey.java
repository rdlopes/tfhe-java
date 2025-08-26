package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.ClientKeyBindings;

import java.lang.foreign.MemorySegment;

public record ClientKey(MemorySegment address) {

  public ClientKey() {
    this(ClientKeyBindings.allocate());
  }

  public static ClientKey deserialize(DynamicBufferView bufferView) {
    ClientKey clientKey = new ClientKey();
    ClientKeyBindings.deserialize(bufferView.address(), clientKey.address());
    return clientKey;
  }

  public static ClientKey safeDeserialize(DynamicBufferView bufferView) {
    ClientKey clientKey = new ClientKey();
    ClientKeyBindings.safeDeserialize(bufferView.address(), clientKey.address());
    return clientKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    ClientKeyBindings.serialize(address, dynamicBuffer.address());
    return dynamicBuffer;
  }

  public DynamicBuffer safeSerialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    ClientKeyBindings.safeSerialize(address, dynamicBuffer.address());
    return dynamicBuffer;
  }
}
