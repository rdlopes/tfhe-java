package io.github.rdlopes.tfhe.core.serde;

import io.github.rdlopes.tfhe.ffm.AddressLayoutPointer;

import java.io.*;

public abstract class FheAbstractSerializable extends AddressLayoutPointer implements FheSerializable {

  @Serial
  private void writeObject(ObjectOutputStream out) throws IOException {
    out.defaultWriteObject();
    DynamicBuffer buffer = serialize();
    byte[] data = buffer.view()
                        .toByteArray();
    out.writeInt(data.length);
    out.write(data);
  }

  @Serial
  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    in.defaultReadObject();
    int size = in.readInt();
    byte[] data = new byte[size];
    in.readFully(data);
    DynamicBufferView bufferView = DynamicBufferView.fromByteArray(data);
    deserialize(bufferView);
  }

  @Serial
  private void readObjectNoData() throws ObjectStreamException {
    throw new InvalidObjectException("Stream data required");
  }
}
