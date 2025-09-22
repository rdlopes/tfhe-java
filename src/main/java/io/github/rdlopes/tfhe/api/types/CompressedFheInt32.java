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

public class CompressedFheInt32 extends NativePointer
  implements CompressedFheType<Integer, FheInt32, CompressedFheInt32> {
  private static final Logger logger = LoggerFactory.getLogger(CompressedFheInt32.class);

  /**
   {@snippet lang = "c":

     ptr can be null (no-op in that case)

     int compressed_fhe_int32_destroy(struct CompressedFheInt32 *ptr);
     }
   */
  CompressedFheInt32() {
    logger.trace("init");
    super(TfheHeader::compressed_fhe_int32_destroy);
  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt32 deserialize(DynamicBuffer dynamicBuffer, ServerKey serverKey) {
    CompressedFheInt32 deserialized = new CompressedFheInt32();
    execute(() -> compressed_fhe_int32_safe_deserialize_conformant(dynamicBuffer.getAddress(), BUFFER_MAX_SIZE, serverKey.getValue(), deserialized.getAddress()));
    return deserialized;

  }

  /**
   {@snippet lang = "c":

     }
   */
  public static CompressedFheInt32 encrypt(Integer clearValue, ClientKey clientKey) {
    CompressedFheInt32 encrypted = new CompressedFheInt32();
    execute(() -> compressed_fhe_int32_try_encrypt_with_client_key_i32(clearValue, clientKey.getValue(), encrypted.getAddress()));
    return encrypted;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public FheInt32 decompress() {
    FheInt32 decompressed = new FheInt32();
    execute(() -> compressed_fhe_int32_decompress(getValue(), decompressed.getAddress()));
    return decompressed;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  public DynamicBuffer serialize() {
    DynamicBuffer dynamicBuffer = new DynamicBuffer();
    execute(() -> compressed_fhe_int32_safe_serialize(getValue(), dynamicBuffer.getAddress(), BUFFER_MAX_SIZE));

    return dynamicBuffer;

  }

  /**
   {@snippet lang = "c":

     }
   */
  @Override
  @SuppressWarnings("MethodDoesntCallSuperMethod")
  public CompressedFheInt32 clone() {
    CompressedFheInt32 cloned = new CompressedFheInt32();
    execute(() -> compressed_fhe_int32_clone(getValue(), cloned.getAddress()));
    return cloned;

  }
}
