package io.github.rdlopes.tfhe.api.types;

import io.github.rdlopes.tfhe.api.CompressedFheType;
import io.github.rdlopes.tfhe.api.keys.ClientKey;
import io.github.rdlopes.tfhe.api.keys.ServerKey;
import io.github.rdlopes.tfhe.api.serde.DynamicBuffer;
import io.github.rdlopes.tfhe.ffm.NativePointer;
import io.github.rdlopes.tfhe.ffm.TfheHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.rdlopes.tfhe.ffm.NativeCall.execute;
import static io.github.rdlopes.tfhe.ffm.TfheHeader.*;

public class CompressedFheUint8 extends NativePointer
  implements CompressedFheType<Byte, FheUint8, CompressedFheUint8> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint8.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint8_destroy(struct CompressedFheUint8 *ptr);
     }
   */
  CompressedFheUint8() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint8_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint8 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint8 deserialized = new CompressedFheUint8();
    execute(() -> compressed_fhe_uint8_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint8 encrypt(Byte clearValue, ClientKey clientKey) {
    CompressedFheUint8 encrypted = new CompressedFheUint8();
    execute(() -> compressed_fhe_uint8_try_encrypt_with_client_key_u8(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint8 decompress() {
    FheUint8 decompressed = new FheUint8();
    execute(() -> compressed_fhe_uint8_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint8_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint8 clone() {
    CompressedFheUint8 cloned = new CompressedFheUint8();
    execute(() -> compressed_fhe_uint8_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
