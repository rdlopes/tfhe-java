package io.github.rdlopes.tfhe.api.shortint;

import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.core.ffm.NativePointer;
import io.github.rdlopes.tfhe.core.ffm.TfheHeader;

import static io.github.rdlopes.tfhe.core.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.core.ffm.TfheHeader.*;

public class ShortintCompressedServerKey extends NativePointer {

  private ShortintCompressedServerKey() {
    super(TfheHeader::shortint_destroy_compressed_server_key);
  }

  public static ShortintCompressedServerKey generate(ShortintClientKey clientKey) {
    ShortintCompressedServerKey serverKey = new ShortintCompressedServerKey();
    execute(() -> shortint_gen_compressed_server_key(clientKey.getValue(), serverKey.getAddress()));
    return serverKey;
  }

  public static ShortintCompressedServerKey deserialize(DynamicBuffer dynamicBuffer) {
    ShortintCompressedServerKey serverKey = new ShortintCompressedServerKey();
    execute(() -> shortint_deserialize_compressed_server_key(dynamicBuffer.getAddress(), serverKey.getAddress()));
    return serverKey;
  }

  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> shortint_serialize_compressed_server_key(getValue(), dynamicBuffer.getAddress()));
    return dynamicBuffer;
  }

  public ShortintServerKey decompress() {
    ShortintServerKey result = new ShortintServerKey();
    execute(() -> shortint_decompress_server_key(getValue(), result.getAddress()));
    return result;
  }
}
