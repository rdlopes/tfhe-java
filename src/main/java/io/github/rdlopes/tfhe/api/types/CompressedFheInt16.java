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

public class CompressedFheInt16 extends NativePointer
  implements CompressedFheType<Short, FheInt16, CompressedFheInt16> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt16.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int16_destroy(struct CompressedFheInt16 *ptr);
     }
   */
  CompressedFheInt16() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int16_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt16 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt16 deserialized = new CompressedFheInt16();
    execute(() -> compressed_fhe_int16_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt16 encrypt(Short clearValue, ClientKey clientKey) {
    CompressedFheInt16 encrypted = new CompressedFheInt16();
    execute(() -> compressed_fhe_int16_try_encrypt_with_client_key_i16(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt16 decompress() {
    FheInt16 decompressed = new FheInt16();
    execute(() -> compressed_fhe_int16_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int16_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt16 clone() {
    CompressedFheInt16 cloned = new CompressedFheInt16();
    execute(() -> compressed_fhe_int16_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
