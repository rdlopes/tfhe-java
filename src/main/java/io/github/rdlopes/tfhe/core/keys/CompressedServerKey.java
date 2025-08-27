package io.github.rdlopes.tfhe.core.keys;

import io.github.rdlopes.tfhe.core.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.serde.DynamicBufferView;
import io.github.rdlopes.tfhe.ffm.CompressedServerKeyBindings;

import java.lang.foreign.MemorySegment;

public record CompressedServerKey(MemorySegment address) {

  public CompressedServerKey() {
    this(CompressedServerKeyBindings.allocate());
  }

  public CompressedServerKey(ClientKey clientKey) {
    this();
    CompressedServerKeyBindings.createNew(clientKey.address(), address);
  }

  public static CompressedServerKey deserialize(DynamicBufferView bufferView) {
    CompressedServerKey compressedServerKey = new CompressedServerKey();
    CompressedServerKeyBindings.deserialize(bufferView.address(), compressedServerKey.address());
    return compressedServerKey;
  }

  public static CompressedServerKey safeDeserialize(DynamicBufferView bufferView) {
    CompressedServerKey compressedServerKey = new CompressedServerKey();
    CompressedServerKeyBindings.safeDeserialize(bufferView.address(), compressedServerKey.address());
    return compressedServerKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    CompressedServerKeyBindings.serialize(address, dynamicBuffer.address());
    return dynamicBuffer;
  }

  public DynamicBuffer safeSerialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    CompressedServerKeyBindings.safeSerialize(address, dynamicBuffer.address());
    return dynamicBuffer;
  }

  public ServerKey decompress() {
    ServerKey serverKey = new ServerKey();
    CompressedServerKeyBindings.decompress(address, serverKey.address());
    return serverKey;
  }

  public void destroy() {
    CompressedServerKeyBindings.destroy(address);
  }
}
