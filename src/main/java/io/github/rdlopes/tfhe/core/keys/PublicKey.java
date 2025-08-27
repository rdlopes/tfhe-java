package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.PublicKeyBindings;

import java.lang.foreign.MemorySegment;

public record PublicKey(MemorySegment address) {

  public PublicKey() {
    this(PublicKeyBindings.allocate());
  }

  public static PublicKey deserialize(DynamicBufferView bufferView) {
    PublicKey publicKey = new PublicKey();
    PublicKeyBindings.deserialize(bufferView.address(), publicKey.address());
    return publicKey;
  }

  public static PublicKey safeDeserialize(DynamicBufferView bufferView) {
    PublicKey publicKey = new PublicKey();
    PublicKeyBindings.safeDeserialize(bufferView.address(), publicKey.address());
    return publicKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    PublicKeyBindings.serialize(address, dynamicBuffer.address());
    return dynamicBuffer;
  }

  public DynamicBuffer safeSerialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    PublicKeyBindings.safeSerialize(address, dynamicBuffer.address());
    return dynamicBuffer;
  }

  public void destroy() {
    PublicKeyBindings.destroy(address);
  }
}
