package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.CompactPublicKeyBindings;

import java.lang.foreign.MemorySegment;

public record CompactPublicKey(MemorySegment address) {

  public CompactPublicKey() {
    this(CompactPublicKeyBindings.allocate());
  }

  public static CompactPublicKey deserialize(DynamicBufferView bufferView) {
    CompactPublicKey compactPublicKey = new CompactPublicKey();
    CompactPublicKeyBindings.deserialize(bufferView.address(), compactPublicKey.address());
    return compactPublicKey;
  }

  public static CompactPublicKey safeDeserialize(DynamicBufferView bufferView) {
    CompactPublicKey compactPublicKey = new CompactPublicKey();
    CompactPublicKeyBindings.safeDeserialize(bufferView.address(), compactPublicKey.address());
    return compactPublicKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    CompactPublicKeyBindings.serialize(address, dynamicBuffer.address());
    return dynamicBuffer;
  }

  public DynamicBuffer safeSerialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    CompactPublicKeyBindings.safeSerialize(address, dynamicBuffer.address());
    return dynamicBuffer;
  }

  public void destroy() {
    CompactPublicKeyBindings.destroy(address);
  }
}
