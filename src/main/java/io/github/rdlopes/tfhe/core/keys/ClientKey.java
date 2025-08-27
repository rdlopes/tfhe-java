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

  public PublicKey generatePublicKey() {
    PublicKey publicKey = new PublicKey();
    ClientKeyBindings.generatePublicKey(address, publicKey.address());
    return publicKey;
  }

  public CompactPublicKey generateCompactPublicKey() {
    CompactPublicKey compactPublicKey = new CompactPublicKey();
    ClientKeyBindings.generateCompactPublicKey(address, compactPublicKey.address());
    return compactPublicKey;
  }

  public CompressedServerKey generateCompressedPublicKey() {
    CompressedServerKey compressedServerKey = new CompressedServerKey();
    ClientKeyBindings.generateCompressedPublicKey(address, compressedServerKey.address());
    return compressedServerKey;
  }

  public void destroy() {
    ClientKeyBindings.destroy(address);
  }
}
