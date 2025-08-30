package io.github.rdlopes.tfhe.core.serde;

import java.io.Serial;
import java.io.Serializable;

public interface FheSerializable extends Serializable {
  @Serial
  long serialVersionUID = 1L;
  long BUFFER_MAX_SIZE = Integer.MAX_VALUE;

  DynamicBuffer serialize();

  void deserialize(DynamicBufferView bufferView);
}
