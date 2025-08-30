package io.github.rdlopes.tfhe.core.serde;

import java.io.Serializable;

public interface FheSerializable<T> extends Serializable {
  long BUFFER_MAX_SIZE = Integer.MAX_VALUE;

  default DynamicBuffer serialize() {
    throw new UnsupportedOperationException("Serialization not implemented");
  }

  default void deserialize(DynamicBufferView bufferView) {
    throw new UnsupportedOperationException("Deserialization not implemented");
  }
}
