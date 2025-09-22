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

public class CompressedFheInt8 extends NativePointer
  implements CompressedFheType<Byte, FheInt8, CompressedFheInt8> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt8.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int8_destroy(struct CompressedFheInt8 *ptr);
     }
   */
  CompressedFheInt8() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int8_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt8 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt8 deserialized = new CompressedFheInt8();
    execute(() -> compressed_fhe_int8_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt8 encrypt(Byte clearValue, ClientKey clientKey) {
    CompressedFheInt8 encrypted = new CompressedFheInt8();
    execute(() -> compressed_fhe_int8_try_encrypt_with_client_key_i8(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt8 decompress() {
    FheInt8 decompressed = new FheInt8();
    execute(() -> compressed_fhe_int8_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int8_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt8 clone() {
    CompressedFheInt8 cloned = new CompressedFheInt8();
    execute(() -> compressed_fhe_int8_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
