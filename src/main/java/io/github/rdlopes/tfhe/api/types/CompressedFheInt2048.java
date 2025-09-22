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

public class CompressedFheInt2048 extends NativePointer
  implements CompressedFheType<I2048, FheInt2048, CompressedFheInt2048> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt2048.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int2048_destroy(struct CompressedFheInt2048 *ptr);
     }
   */
  CompressedFheInt2048() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int2048_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt2048 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt2048 deserialized = new CompressedFheInt2048();
    execute(() -> compressed_fhe_int2048_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt2048 encrypt(I2048 clearValue, ClientKey clientKey) {
    CompressedFheInt2048 encrypted = new CompressedFheInt2048();
    execute(() -> compressed_fhe_int2048_try_encrypt_with_client_key_i2048(clearValue.getAddress(), clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt2048 decompress() {
    FheInt2048 decompressed = new FheInt2048();
    execute(() -> compressed_fhe_int2048_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int2048_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt2048 clone() {
    CompressedFheInt2048 cloned = new CompressedFheInt2048();
    execute(() -> compressed_fhe_int2048_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
