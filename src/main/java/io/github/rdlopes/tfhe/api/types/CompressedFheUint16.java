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

public class CompressedFheUint16 extends NativePointer
  implements CompressedFheType<Short, FheUint16, CompressedFheUint16> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheUint16.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_uint16_destroy(struct CompressedFheUint16 *ptr);
     }
   */
  CompressedFheUint16() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_uint16_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint16 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheUint16 deserialized = new CompressedFheUint16();
    execute(() -> compressed_fhe_uint16_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheUint16 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheUint16 encrypted = new CompressedFheUint16();
    execute(() -> compressed_fhe_uint16_try_encrypt_with_client_key_u16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheUint16 decompress() {
    FheUint16 decompressed = new FheUint16();
    execute(() -> compressed_fhe_uint16_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_uint16_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheUint16 clone() {
    CompressedFheUint16 cloned = new CompressedFheUint16();
    execute(() -> compressed_fhe_uint16_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
